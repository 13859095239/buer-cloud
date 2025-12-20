package com.buer.flow.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.buer.common.core.entity.R;
import com.buer.common.core.exception.CheckException;
import com.buer.flow.api.dto.CreateProcinstDTO;
import com.buer.flow.api.entity.ActHiProcinst;
import com.buer.flow.api.query.ProcinstQuery;
import com.buer.flow.api.vo.*;
import com.buer.flow.biz.mapper.ActHiProcinstMapper;
import com.buer.flow.biz.service.ActHiProcinstService;
import com.buer.flow.biz.service.ActReModelService;
import com.buer.flow.biz.service.ActReProcdefService;
import com.buer.flow.biz.service.ActRuTaskService;
import com.buer.flow.biz.utils.FlowSecurityUtil;
import com.buer.system.api.feign.RemoteCodeRuleService;
import com.buer.system.api.filler.UserLabelFiller;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.IdentityLinkInfo;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.buer.flow.api.entity.table.ActHiProcinstTableDef.ACT_HI_PROCINST;
import static com.buer.flow.api.entity.table.ActReModelTableDef.ACT_RE_MODEL;
import static com.buer.flow.api.entity.table.ActReProcdefTableDef.ACT_RE_PROCDEF;

/**
 * 流程实例 ServiceImpl
 *
 * @author zoulan
 * @since 2023-05-11
 */
@Service
@RequiredArgsConstructor
public class ActHiProcinstServiceImpl extends ServiceImpl<ActHiProcinstMapper, ActHiProcinst> implements ActHiProcinstService {

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final RemoteCodeRuleService remoteCodeRuleService;
    private final ActRuTaskService actRuTaskService;
    private final ActReModelService actReModelService;
    private final RepositoryService repositoryService;
    private final ActReProcdefService actReProcdefService;
    private final UserLabelFiller userLabelFiller;

    /**
     * 通过id查询流程实例
     *
     * @param id id
     * @return ProcinstVO
     */
    @Override
    public ProcinstVO getProcinstById(String id) {
        return getQueryChain().and(ACT_HI_PROCINST.ID.eq(id))
            .oneAs(ProcinstVO.class);
    }


    /**
     * 通过模型key获取新建流程实例信息
     *
     * @param modelKey 模型key
     * @return 流程实例信息 VO
     */
    @Override
    public CreateProcinstInfoVO getCreateProcinstInfo(String modelKey) {
        ModelVO modelVO = actReModelService.getModelByKey(modelKey);
        String procDefId = actReProcdefService.getNewestProcessDefinitionByModelKey(modelKey)
            .getId();
        CreateProcinstInfoVO createProcinstInfoVO = new CreateProcinstInfoVO()
            .setProcDefId(procDefId)
            .setModelKey(modelVO.getKey())
            .setModelName(modelVO.getName())
            .setModelWebPath(modelVO.getWebPath());
        return createProcinstInfoVO;
    }

    /**
     * 通过流程实例ID获取流程信息
     *
     * @param procinstId 流程实例id
     * @return 流程实例信息 VO
     */
    @Override
    public ProcinstInfoVO getProcinstInfoById(String procinstId) {
        ProcinstVO procinstVO = getProcinstById(procinstId);
        ProcinstInfoVO procinstInfoVO = new ProcinstInfoVO();
        BeanUtil.copyProperties(procinstVO, procinstInfoVO);
        procinstInfoVO.setProcInstName(procinstVO.getName());
        return procinstInfoVO;
    }

    /**
     * 通过id修改流程实例
     *
     * @param procinst 流程实例对象
     * @return Boolean
     */
    @Override
    public boolean updateProcinst(ActHiProcinst procinst) {
        return updateById(procinst);
    }

    /**
     * 通过id删除流程实例
     *
     * @param ids ids
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean removeProcinstByIds(List<String> ids) {
        runtimeService.bulkDeleteProcessInstances(ids, "删除");
        return removeByIds(ids);
    }

    /**
     * 列表查询流程实例
     *
     * @param procinstQuery 查询对象
     * @return 列表
     */
    @Override
    public List<ProcinstVO> listProcinst(ProcinstQuery procinstQuery) {
        List<ProcinstVO> dataList = getQueryChainByQuery(procinstQuery).listAs(ProcinstVO.class);
        assignTasksToProcinstVO(dataList);
        return dataList;
    }

    /**
     * 分页查询流程实例
     *
     * @param page          分页对象
     * @param procinstQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<ProcinstVO> pageProcinst(Page<ProcinstVO> page, ProcinstQuery procinstQuery) {
        Page<ProcinstVO> dataPage = getQueryChainByQuery(procinstQuery).pageAs(page, ProcinstVO.class);
        assignTasksToProcinstVO(dataPage.getRecords());
        fillTaskinst(dataPage.getRecords());
        return dataPage;
    }

    /**
     * 获取QueryChain对象
     */
    QueryChain<ActHiProcinst> getQueryChain() {
        return queryChain().select(ACT_HI_PROCINST.ALL_COLUMNS,
                ACT_RE_MODEL.KEY.as(ProcinstVO::getModalKey),
                ACT_RE_MODEL.NAME.as(ProcinstVO::getName),
                ACT_RE_MODEL.WEB_PATH.as(ProcinstVO::getModelWebPath)
            )
            .leftJoin(ACT_RE_PROCDEF)
            .on(ACT_RE_PROCDEF.ID.eq(ACT_HI_PROCINST.PROC_DEF_ID))
            .leftJoin(ACT_RE_MODEL)
            .on(ACT_RE_MODEL.KEY.eq(ACT_RE_PROCDEF.KEY));
    }

    /**
     * 通过userQuery获取QueryChain对象
     */
    private QueryChain<ActHiProcinst> getQueryChainByQuery(ProcinstQuery procinstQuery) {
        return getQueryChain()
            .and(ACT_HI_PROCINST.ID.eq(procinstQuery.getBusinessKey()))
            .and(ACT_HI_PROCINST.NAME.like(procinstQuery.getProcinstName()))
            .orderBy(ACT_HI_PROCINST.START_TIME.desc());
    }

    /**
     * 创建流程实例
     *
     * @param createProcinstDTO 创建流程对象
     * @return createProcinstDTO
     */
    @Override
    @Transactional
    public CreatedProcinstVO createProcinst(CreateProcinstDTO createProcinstDTO) {
        ModelVO modelVO = actReModelService.getModelByKey(createProcinstDTO.getModelKey());
        R<String> result = remoteCodeRuleService.generateCodeById(modelVO.getCodeRuleId());
        String businessKey = result.getData();
        FlowSecurityUtil.loginAs();
        // 创建后的流程实例
        ProcessInstance processInstance = runtimeService
            // 使用 ProcessInstanceBuilder 灵活的启动流程
            .createProcessInstanceBuilder()
            // 设置流程 key
            .processDefinitionKey(createProcinstDTO.getModelKey())
            // 设置流水号
            .businessKey(businessKey)
            // 设置实例名称
            .name(modelVO.getName())
            // 设置普通流程变量
            // .variable("assigneeList", Arrays.asList("1","2"))
            // 指定任务负责人（如有权限）
            // .assignee("alice")
            // 启动实例
            .start();
        //        var definition = repositoryService
        //                .createProcessDefinitionQuery()
        //                .processDefinitionKey(createProcinstDTO.getModelKey())
        //                .latestVersion()
        //                .singleResult();
        //        BpmnModel bpmnModel = repositoryService.getBpmnModel(definition.getId());
        //        // 获取流程启动后的第一个任务,并完成。
        //        Task task = taskService.createTaskQuery()
        //                .processInstanceId(processInstance.getId())
        //                .active()
        //                .singleResult();
        //        if (task != null) {
        //            actRuTaskService.completeTask(
        //                    new CompleteTaskDTO()
        //                            .setTaskId(task.getId()));
        //        }

        return new CreatedProcinstVO()
            .setProcInstId(processInstance.getId())
            .setBusinessKey(processInstance.getBusinessKey())
            .setProcStartTime(LocalDateTime.now());
    }

    /**
     * 激活流程实例
     *
     * @param procInstId 流程实例id
     * @return Boolean
     */
    @Override
    public Boolean activateProcinstById(String procInstId) {
        var processInstance = runtimeService.createProcessInstanceQuery()
            .processInstanceId(procInstId)
            .singleResult();
        // 流程实例暂停状态
        boolean suspended = processInstance.isSuspended();
        runtimeService.activateProcessInstanceById(procInstId);
        if (!suspended) {
            throw new CheckException("流程实例已经处于激活状态");
        }
        return true;
    }

    /**
     * 挂起流程实例
     *
     * @param procInstId 流程实例id
     * @return Boolean
     */
    @Override
    public Boolean suspendProcinstById(String procInstId) {
        var processInstance = runtimeService.createProcessInstanceQuery()
            .processInstanceId(procInstId)
            .singleResult();
        // 流程实例暂停状态
        boolean suspended = processInstance.isSuspended();
        runtimeService.suspendProcessInstanceById(procInstId);
        if (suspended) {
            throw new CheckException("流程实例已经处于暂停状态");
        }
        return true;
    }

    /**
     * 根据流程实例列表批量赋值办理人和候选人
     */
    private void assignTasksToProcinstVO(List<ProcinstVO> procinstVOList) {
        if (procinstVOList == null || procinstVOList.isEmpty()) {
            return;
        }

        // 获取所有流程实例ID
        List<String> processInstanceIds = procinstVOList.stream()
            .map(ProcinstVO::getProcInstId)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        if (processInstanceIds.isEmpty()) {
            return;
        }

        // 查询所有相关的待办任务
        List<Task> tasks = taskService.createTaskQuery()
            .processInstanceIdIn(processInstanceIds)
            .active()
            .list();

        if (tasks.isEmpty()) {
            return;
        }

        // 使用专门的数据结构存储任务信息
        Map<String, TaskInfo> taskInfoMap = new HashMap<>();

        // 批量处理任务信息
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            if (processInstanceId == null) {
                continue;
            }

            TaskInfo taskInfo = taskInfoMap.computeIfAbsent(processInstanceId, k -> new TaskInfo());

            // 处理办理人
            String assignee = task.getAssignee();
            if (assignee != null) {
                taskInfo.addAssignee(assignee);
            }

            // 处理候选人
            List<String> candidateUsers = getCandidateUsers(task.getId());
            taskInfo.addCandidateUsers(candidateUsers);
        }

        // 将任务信息赋值到ProcinstVO
        for (ProcinstVO procinstVO : procinstVOList) {
            TaskInfo taskInfo = taskInfoMap.get(procinstVO.getProcInstId());
            if (taskInfo != null) {
                taskInfo.applyToProcinstVO(procinstVO);
            }
        }
    }

    /**
     * 获取任务的候选人列表
     */
    private List<String> getCandidateUsers(String taskId) {
        return taskService.getIdentityLinksForTask(taskId)
            .stream()
            .filter(identityLink -> "candidate".equals(identityLink.getType()))
            .map(IdentityLinkInfo::getUserId)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    /**
     * 填充外键信息
     *
     * @param taskinstVOList 列表
     */
    private void fillTaskinst(List<ProcinstVO> taskinstVOList) {
        userLabelFiller.fillField(taskinstVOList,
            ProcinstVO::getStartUserId,
            ProcinstVO::setStartUserName);
    }

    /**
     * 任务信息内部类，用于存储和管理任务相关的用户信息
     */
    private static class TaskInfo {
        private final Set<String> assignees = new HashSet<>();
        private final Set<String> candidateUsers = new HashSet<>();

        public void addAssignee(String assignee) {
            if (assignee != null) {
                assignees.add(assignee);
            }
        }

        public void addCandidateUsers(List<String> candidateUsers) {
            if (candidateUsers != null) {
                this.candidateUsers.addAll(candidateUsers);
            }
        }

        public void applyToProcinstVO(ProcinstVO procinstVO) {
            if (!assignees.isEmpty()) {
                procinstVO.setAssigneesIds(StrUtil.join(",", assignees));
                // TODO: 根据assignees的ID查询对应的用户名
                // 暂时使用ID作为名称，后续可以优化为查询用户表获取真实姓名
                procinstVO.setAssigneesName(StrUtil.join(",", assignees));
            }

            if (!candidateUsers.isEmpty()) {
                procinstVO.setCandidateUsersName(StrUtil.join(",", candidateUsers));
            }
        }
    }
}
