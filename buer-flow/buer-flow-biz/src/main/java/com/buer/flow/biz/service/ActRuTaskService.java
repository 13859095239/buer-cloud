package com.buer.flow.biz.service;

import com.buer.flow.api.dto.CompleteTaskDTO;
import com.buer.flow.api.dto.FreeJumpDTO;
import com.buer.flow.api.dto.JumpToHistoricDTO;
import com.buer.flow.api.entity.ActRuTask;
import com.buer.flow.api.query.TaskForMyQuery;
import com.buer.flow.api.query.TaskQuery;
import com.buer.flow.api.vo.CompletedTaskVO;
import com.buer.flow.api.vo.TaskVO;
import com.buer.flow.api.vo.TodoTaskInfoVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 待办任务 Service
 *
 * @author zoulan
 * @since 2023-05-11
 */
public interface ActRuTaskService extends IService<ActRuTask> {

    /**
     * 通过id查询
     *
     * @param id id
     * @return 待办任务信息
     */
    TaskVO getTaskById(String id);

    /**
     * 通过任务id获取我的待办任务信息
     *
     * @param taskId 任务id
     * @return 办理任务信息VO
     */
    TodoTaskInfoVO getMyTodoTaskInfo(String taskId);

    /**
     * 新增待办任务
     *
     * @param actRuTask 待办任务对象
     * @return Boolean
     */
    boolean addTask(ActRuTask actRuTask);

    /**
     * 通过id修改待办任务
     *
     * @param actRuTask 待办任务对象
     * @return Boolean
     */
    boolean updateTask(ActRuTask actRuTask);

    /**
     * 通过ids删除待办任务
     *
     * @param ids ids
     * @return Boolean
     */
    boolean deleteTaskByIds(List<String> ids);

    /**
     * 列表查询待办任务
     *
     * @param taskQuery 查询对象
     * @return 列表
     */
    List<TaskVO> listTask(TaskQuery taskQuery);

    /**
     * 分页查询待办任务
     *
     * @param page      分页对象
     * @param taskQuery 查询对象
     * @return 分页对象
     */
    Page<TaskVO> pageTask(Page<TaskVO> page, TaskQuery taskQuery);

    /**
     * 完成任务
     *
     * @param completeTaskDTO 完成任务DTO
     * @return Boolean
     */
    CompletedTaskVO completeTask(CompleteTaskDTO completeTaskDTO);

    /**
     * 分页查询,我的待办
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    Page<TaskVO> pageMyTodoTask(Page<TaskVO> page, TaskForMyQuery entity);

    /**
     * 完成任务，跳转到历史办理完成节点
     *
     * @param jumpToHistoricDTO 跳转到历史任务DTO
     * @return Boolean
     */
    Boolean jumpToHistoric(JumpToHistoricDTO jumpToHistoricDTO);

    /**
     * 完成任务，跳转到任意节点
     *
     * @param freeJumpDTO 跳转到任意DTO
     * @return Boolean
     */
    Boolean freeJump(FreeJumpDTO freeJumpDTO);

    Boolean rejectTask(String taskId);

    /**
     * 用户认领任务
     * 从候选池到待办
     *
     * @param taskId 任务id
     * @return Boolean
     */
    Boolean claimTask(String taskId);

    /**
     * 用户撤销认领任务
     * 退回到候选池
     *
     * @param taskId 任务id
     * @return Boolean
     */
    Boolean unclaimTask(String taskId);
}