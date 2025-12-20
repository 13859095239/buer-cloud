package com.buer.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.flow.api.dto.CompleteTaskDTO;
import com.buer.flow.api.dto.CreateProcinstDTO;
import com.buer.flow.api.dto.ListenerDTO;
import com.buer.system.api.dto.LeaveDTO;
import com.buer.system.api.query.LeaveQuery;
import com.buer.system.api.vo.LeaveVO;
import com.buer.system.biz.service.SysLeaveService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 请假 Controller
 *
 * @author zoulan
 * @since 2024-06-07
 */
@RestController
@RequestMapping("leave")
@RequiredArgsConstructor
@Tag(name = "请假")
public class SysLeaveController {

    private final SysLeaveService service;

    /**
     * 通过id查询请假
     *
     * @param id id
     * @return LeaveVO
     */
    @Operation(summary = "通过id查询请假")
    @GetMapping(value = "/{id}")
    public R<LeaveVO> getLeaveById(@PathVariable Long id) {
        return R.ok(service.getLeaveById(id));
    }

    /**
     * 通过流程实例id查询请假
     *
     * @param procInstId 流程实例id
     * @return LeaveVO
     */
    @Operation(summary = "通过id查询请假")
    @GetMapping(value = "/procInstId/{procInstId}")
    public R<LeaveVO> getLeaveByProcInstId(@PathVariable String procInstId) {
        return R.ok(service.getLeaveByProcInstId(procInstId));
    }


    /**
     * 新增请假
     *
     * @param leaveDTO 请假信息
     * @return boolean
     */
    @SysLog(value = "新增请假")
    @Operation(summary = "新增请假")
    @PostMapping
    @SaCheckPermission("leave-add")
    public R<Boolean> addLeave(@RequestBody LeaveDTO<CreateProcinstDTO> leaveDTO) {
        return R.ok(service.addLeave(leaveDTO));
    }

    /**
     * 通过id修改请假
     *
     * @param leaveDTO 请假信息
     * @return boolean
     */
    @SysLog(value = "修改请假")
    @Operation(summary = "通过id修改请假")
    @PutMapping
    @SaCheckPermission("leave-edit")
    public R<Boolean> updateLeave(@RequestBody LeaveDTO leaveDTO) {
        return R.ok(service.updateLeave(leaveDTO));
    }

    /**
     * 通过id删除请假
     *
     * @param leaveIds leaveIds
     * @return boolean
     */
    @SysLog(value = "删除请假")
    @Operation(summary = "通过id删除请假")
    @DeleteMapping
    @SaCheckPermission("leave-delete")
    public R<Boolean> removeLeaveByIds(@RequestBody List<Long> leaveIds) {
        return R.ok(service.removeLeaveByIds(leaveIds));
    }

    /**
     * 列表查询请假
     *
     * @param leaveQuery 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询请假")
    @GetMapping(value = "/list")
    public R<List<LeaveVO>> listLeave(LeaveQuery leaveQuery) {
        return R.ok(service.listLeave(leaveQuery));
    }

    /**
     * 分页查询请假
     *
     * @param page       分页对象
     * @param leaveQuery 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询请假")
    @GetMapping(value = "/page")
    public R<Page<LeaveVO>> pageLeave(Page<LeaveVO> page, LeaveQuery leaveQuery) {
        return R.ok(service.pageLeave(page, leaveQuery));
    }

    /**
     * 完成流程任务
     *
     * @param leaveDTO leaveDTO
     * @return Boolean
     */
    @PostMapping("/completeTask")
    public R<Boolean> completeTask(@RequestBody LeaveDTO<CompleteTaskDTO> leaveDTO) {
        return R.ok(service.completeLeave(leaveDTO));
    }

    /**
     * 流程事件通知
     *
     * @param listenerDTO 流程监听事件DTO
     * @return Boolean
     */
    @PostMapping("/flowListener")

    public R<Boolean> flowListener(@RequestBody ListenerDTO listenerDTO) {
        return R.ok(service.flowListener(listenerDTO));
    }

}