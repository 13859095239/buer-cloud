package com.buer.flow.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.buer.common.core.exception.CheckException;
import com.buer.common.security.util.SecurityUtils;
import com.buer.flow.api.dto.CompleteTaskDTO;
import com.buer.flow.api.dto.FreeJumpDTO;
import com.buer.flow.api.dto.JumpToHistoricDTO;
import com.buer.flow.api.entity.ActRuTask;
import com.buer.flow.api.query.TaskForMyQuery;
import com.buer.flow.api.query.TaskQuery;
import com.buer.flow.api.vo.CompletedTaskVO;
import com.buer.flow.api.vo.TaskVO;
import com.buer.flow.api.vo.TodoTaskInfoVO;
import com.buer.flow.biz.mapper.ActRuTaskMapper;
import com.buer.flow.biz.service.ActRuTaskService;
import com.buer.flow.biz.utils.FlowSecurityUtil;
import com.buer.system.api.filler.UserLabelFiller;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.buer.flow.api.entity.table.ActHiProcinstTableDef.ACT_HI_PROCINST;
import static com.buer.flow.api.entity.table.ActReModelTableDef.ACT_RE_MODEL;
import static com.buer.flow.api.entity.table.ActReProcdefTableDef.ACT_RE_PROCDEF;
import static com.buer.flow.api.entity.table.ActRuExecutionTableDef.ACT_RU_EXECUTION;
import static com.buer.flow.api.entity.table.ActRuIdentitylinkTableDef.ACT_RU_IDENTITYLINK;
import static com.buer.flow.api.entity.table.ActRuTaskTableDef.ACT_RU_TASK;
import static com.mybatisflex.core.query.QueryMethods.selectOne;


/**
 * 待办任务 ServiceImpl
 *
 * @author zoulan
 * @since 2023-05-11
 */
@Service
@RequiredArgsConstructor
public class ActRuTaskServiceImpl extends ServiceImpl<ActRuTaskMapper, ActRuTask> implements ActRuTaskService {
    private final TaskService taskService;
    private final HistoryService historyService;
    private final RuntimeService runtimeService;
    private final UserLabelFiller userLabelFiller;

    /**
     * 通过id查询待办任务
     *
     * @param id id
     * @return TaskVO
     */
    @Override
    public TaskVO getTaskById(String id) {
        return getQueryChain().and(ACT_RU_TASK.ID.eq(id))
            .oneAs(TaskVO.class);
    }

    /**
     * 通过任务id获取我的待办任务信息
     *
     * @param taskId 任务id
     * @return 办理任务信息VO
     */
    @Override
    public TodoTaskInfoVO getMyTodoTaskInfo(String taskId) {
        TaskVO task = getTaskById(taskId);
        TodoTaskInfoVO todoTaskInfoVO = new TodoTaskInfoVO();
        BeanUtil.copyProperties(task, todoTaskInfoVO);
        todoTaskInfoVO.setTaskId(taskId);
        todoTaskInfoVO.setTaskName(task.getName());
        return todoTaskInfoVO;
    }

    /**
     * 新增待办任务
     *
     * @param actRuTask 待办任务对象
     * @return Boolean
     */
    @Override
    public boolean addTask(ActRuTask actRuTask) {
        return save(actRuTask);
    }

    /**
     * 通过id修改待办任务
     *
     * @param actRuTask 待办任务对象
     * @return Boolean
     */
    @Override
    public boolean updateTask(ActRuTask actRuTask) {
        return updateById(actRuTask);
    }

    /**
     * 通过id删除待办任务
     *
     * @param ids ids
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean deleteTaskByIds(List<String> ids) {
        // 直接删除任务，不保留历史
        taskService.deleteTasks(ids, true);
        return true;
    }

    @Override
    public List<TaskVO> listTask(TaskQuery taskQuery) {
        return getQueryChainByQuery(taskQuery)
            .listAs(TaskVO.class);
    }

    /**
     * 分页查询
     *
     * @param page      分页对象
     * @param taskQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<TaskVO> pageTask(Page<TaskVO> page, TaskQuery taskQuery) {
        return getQueryChainByQuery(taskQuery)
            .pageAs(page, TaskVO.class);
    }

    /**
     * 完成任务
     *
     * @param completeTaskDTO 完成任务 DTO
     * @return Boolean
     */
    @Override
    @GlobalTransactional
    public CompletedTaskVO completeTask(CompleteTaskDTO completeTaskDTO) {
        String taskId = completeTaskDTO.getTaskId();
        Task task = taskService.createTaskQuery()
            .taskId(taskId)
            .singleResult();
        if (task == null) {
            throw new CheckException("提交失败,任务已结束,请勿重复提交！");
        }
        FlowSecurityUtil.loginAs();
        // 完成任务
        taskService.complete(taskId,
            completeTaskDTO.getVariables(),
            completeTaskDTO.getLocalScope());
        FlowSecurityUtil.loginOut();
        CompletedTaskVO completedTaskVO = new CompletedTaskVO();
        completedTaskVO.setTaskId(taskId);
        return completedTaskVO;
    }

    /**
     * 分页查询,我的待办
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Override
    public Page<TaskVO> pageMyTodoTask(Page<TaskVO> page, TaskForMyQuery entity) {
        TaskQuery taskQuery = new TaskQuery()
            .setAssignee(SecurityUtils.getUserId()
                .toString());
        BeanUtil.copyProperties(entity, taskQuery);
        Page<TaskVO> dataPage = pageTask(page, taskQuery);
        List<TaskVO> taskVOList = dataPage.getRecords();
        fillTask(taskVOList);
        return dataPage;
    }

    /**
     * 获取QueryChain对象
     */
    QueryChain<ActRuTask> getQueryChain() {
        return queryChain().select(
                ACT_RU_TASK.ALL_COLUMNS,
                ACT_HI_PROCINST.BUSINESS_KEY.as(TaskVO::getBusinessKey),
                ACT_HI_PROCINST.NAME.as(TaskVO::getProcInstName),
                ACT_HI_PROCINST.START_USER_ID.as(TaskVO::getStartUserId),
                ACT_RE_MODEL.KEY.as(TaskVO::getModelKey),
                ACT_RE_MODEL.NAME.as(TaskVO::getModelName),
                ACT_RE_MODEL.WEB_PATH.as(TaskVO::getModelWebPath)
            )
            .leftJoin(ACT_RE_PROCDEF)
            .on(ACT_RE_PROCDEF.ID.eq(ACT_RU_TASK.PROC_DEF_ID))
            .leftJoin(ACT_RU_EXECUTION)
            .on(ACT_RU_EXECUTION.ID.eq(ACT_RU_TASK.EXECUTION_ID))
            .leftJoin(ACT_HI_PROCINST)
            .on(ACT_HI_PROCINST.ID.eq(ACT_RU_TASK.PROC_INST_ID))
            .leftJoin(ACT_RE_MODEL)
            .on(ACT_RE_MODEL.KEY.eq(ACT_RE_PROCDEF.KEY));
    }

    /**
     * 通过userQuery获取QueryChain对象
     */
    private QueryChain<ActRuTask> getQueryChainByQuery(TaskQuery taskQuery) {
        return getQueryChain().and(ACT_RU_TASK.ASSIGNEE.eq(taskQuery.getAssignee())
                // 办理人为空,从act_ru_identitylink表中获取候选人/组
                .or(QueryMethods.exists(selectOne().from(ACT_RU_IDENTITYLINK)
                    .where(ACT_RU_IDENTITYLINK.TASK_ID.eq(ACT_RU_TASK.ID))
                    .and(ACT_RU_TASK.ASSIGNEE.isNull()
                        .and(ACT_RU_IDENTITYLINK.USER_ID.eq(taskQuery.getAssignee())
                            .or(ACT_RU_IDENTITYLINK.GROUP_ID.in(List.of("2")))
                        )))))
            .and(ACT_RU_TASK.NAME.like(taskQuery.getTaskName()))
            .and(ACT_HI_PROCINST.BUSINESS_KEY.like(taskQuery.getBusinessKey()))
            .and(ACT_HI_PROCINST.PROC_INST_ID.eq(taskQuery.getProcInstId()))
            .orderBy(ACT_RU_TASK.CREATE_TIME.desc());
    }

    /**
     * 完成任务，跳转到历史办理完成节点
     *
     * @param jumpToHistoricDTO 自由跳转DTO
     * @return Boolean
     */
    @Override
    public Boolean jumpToHistoric(JumpToHistoricDTO jumpToHistoricDTO) {
        String targetTaskDefKey = jumpToHistoricDTO.getTargetTaskDefKey();
        // 当前办理的task
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery()
            .taskId(jumpToHistoricDTO.getSourceTaskId())
            .singleResult();
        freeJump(task, targetTaskDefKey);
        return true;
    }

    /**
     * 完成任务，跳转到任意节点
     *
     * @param freeJumpDTO 跳转到任意DTO
     * @return Boolean
     */
    @Override
    public Boolean freeJump(FreeJumpDTO freeJumpDTO) {
        String targetTaskDefKey = freeJumpDTO.getTargetTaskDefKey();
        // 当前办理的task
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery()
            .taskId(freeJumpDTO.getSourceTaskId())
            .singleResult();
        freeJump(task, targetTaskDefKey);
        return true;
    }

    @Override
    public Boolean rejectTask(String taskId) {
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery()
            .taskId(taskId)
            .singleResult();
        return true;
    }

    /**
     * 用户认领任务
     * 从候选池到待办
     *
     * @param taskId 任务id
     * @return Boolean
     */
    @Override
    public Boolean claimTask(String taskId) {
        taskService.claim(taskId, SecurityUtils.getUserIdAsString());
        return true;
    }

    /**
     * 用户撤销认领任务
     * 退回到候选池
     *
     * @param taskId 任务id
     * @return Boolean
     */
    @Override
    public Boolean unclaimTask(String taskId) {
        taskService.unclaim(taskId);
        return true;
    }

    void freeJump(@NotNull HistoricTaskInstance task, String targetTaskDefKey) {
        String procInstId = task.getProcessInstanceId();
        String sourceTaskDefKey = task.getTaskDefinitionKey();
        runtimeService.createChangeActivityStateBuilder()
            // 当前流程实例
            .processInstanceId(procInstId)
            // 从当前节点跳转到目标节点
            .moveActivityIdTo(sourceTaskDefKey, targetTaskDefKey)
            .changeState();
    }

    /**
     * 填充待办列表外键信息
     *
     * @param taskVOList 列表
     */
    private void fillTask(List<TaskVO> taskVOList) {
        userLabelFiller.fillField(taskVOList,
            TaskVO::getStartUserId,
            TaskVO::setStartUserName);
    }
}