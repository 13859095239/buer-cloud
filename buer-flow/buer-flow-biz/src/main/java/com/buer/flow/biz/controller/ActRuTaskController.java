package com.buer.flow.biz.controller;

import com.buer.common.core.entity.R;
import com.buer.flow.api.dto.CompleteTaskDTO;
import com.buer.flow.api.dto.FreeJumpDTO;
import com.buer.flow.api.dto.JumpToHistoricDTO;
import com.buer.flow.api.query.TaskForMyQuery;
import com.buer.flow.api.vo.CompletedTaskVO;
import com.buer.flow.api.vo.TaskVO;
import com.buer.flow.api.vo.TodoTaskInfoVO;
import com.buer.flow.biz.service.ActRuTaskService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 待办任务 Controller
 *
 * @author zoulan
 * @since 2023-05-11
 */
@RestController
@RequestMapping("task")
@RequiredArgsConstructor
@Tag(name = "待办任务")
public class ActRuTaskController {

    private final ActRuTaskService service;

    /**
     * 通过任务id获取我的待办任务信息
     *
     * @param taskId 任务id
     * @return 办理任务信息VO
     */
    @Operation(summary = "通过任务id获取我的待办任务信息")
    @GetMapping(value = "/myTodoTask/{taskId}")
    public R<TodoTaskInfoVO> getMyTodoTaskInfo(@PathVariable String taskId) {
        return R.ok(service.getMyTodoTaskInfo(taskId));
    }

    /**
     * 分页查询，我的待办信息
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询")
    @GetMapping(value = "/myTodoTask/page")
    public R<Page<TaskVO>> pageMyTodoTask(Page<TaskVO> page, TaskForMyQuery entity) {
        return R.ok(service.pageMyTodoTask(page, entity));
    }

    /**
     * 用户认领任务
     * 从候选池到待办
     *
     * @param taskId 任务id
     * @return Boolean
     */
    @Operation(summary = "完成任务")
    @PostMapping("/claimTask/{taskId}")
    public R<Boolean> claimTask(@PathVariable String taskId) {
        return R.ok(service.claimTask(taskId));
    }

    /**
     * 用户撤销认领任务
     * 退回到候选池
     *
     * @param taskId 任务id
     * @return Boolean
     */
    @Operation(summary = "完成任务")
    @PostMapping("/unclaimTask/{taskId}")
    public R<Boolean> unclaimTask(@PathVariable String taskId) {
        return R.ok(service.unclaimTask(taskId));
    }

    /**
     * 完成任务
     *
     * @param completeTaskDTO 完成任务DTO
     * @return CompletedTaskVO
     */
    @Operation(summary = "完成任务")
    @PostMapping("/completeTask")
    public R<CompletedTaskVO> completeTask(@RequestBody CompleteTaskDTO completeTaskDTO) {
        return R.ok(service.completeTask(completeTaskDTO));
    }

    /**
     * 完成任务，跳转到历史办理完成节点
     *
     * @param jumpToHistoricDTO 自由跳转DTO
     * @return Boolean
     */
    @Operation(summary = "完成任务，跳转到历史办理完成节点")
    @PostMapping("/jumpToHistoric")
    public R<Boolean> jumpToHistoric(@RequestBody JumpToHistoricDTO jumpToHistoricDTO) {
        return R.ok(service.jumpToHistoric(jumpToHistoricDTO));
    }

    /**
     * 完成任务，跳转到任意节点
     *
     * @param freeJumpDTO 跳转到任意DTO
     * @return Boolean
     */
    @Operation(summary = "完成任务")
    @PostMapping("/freeJump")
    public R<Boolean> freeJump(@RequestBody FreeJumpDTO freeJumpDTO) {
        return R.ok(service.freeJump(freeJumpDTO));
    }

    /**
     * 任务回退到上一个步骤
     *
     * @param taskId 当前任务ID
     * @return Boolean
     */
    @Operation(summary = "任务回退到上一个步骤")
    @PostMapping("/rejectTask")
    public R<Boolean> rejectTask(String taskId) {
        return R.ok(service.rejectTask(taskId));
    }

}