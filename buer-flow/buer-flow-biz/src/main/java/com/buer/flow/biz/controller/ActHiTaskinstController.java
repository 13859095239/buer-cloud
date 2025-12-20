package com.buer.flow.biz.controller;

import com.buer.common.core.entity.R;
import com.buer.flow.api.query.TaskinstForMyQuery;
import com.buer.flow.api.query.TaskinstQuery;
import com.buer.flow.api.vo.DoneTaskinstInfoVO;
import com.buer.flow.api.vo.TaskinstVO;
import com.buer.flow.biz.service.ActHiTaskinstService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 历史任务 Controller
 *
 * @author zoulan
 * @since 2023-05-11
 */
@RestController
@RequestMapping("taskinst")
@RequiredArgsConstructor
@Tag(name = "历史任务 Controller")
public class ActHiTaskinstController {

    private final ActHiTaskinstService service;

    /**
     * 通过id查询 历史任务
     *
     * @param id id
     * @return TaskinstVO
     */
    @Operation(summary = "通过id查询")
    @GetMapping(value = "/{id}")
    public R<TaskinstVO> getTaskinstById(@PathVariable String id) {
        return R.ok(service.getTaskinstById(id));
    }

    /**
     * 通过任务ID获取我的已办任务信息
     *
     * @param taskId 任务id
     * @return 已办任务信息 VO
     */
    @Operation(summary = "通过任务ID获取我的待办任务信息")
    @GetMapping(value = "/myDoneTaskinst/{taskId}")
    public R<DoneTaskinstInfoVO> getMyDoneTaskinstInfo(@PathVariable String taskId) {
        return R.ok(service.getMyDoneTaskinstInfo(taskId));
    }

    /**
     * 分页查询，历史任务
     *
     * @param page          分页对象
     * @param taskinstQuery 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询")
    @GetMapping(value = "/page")
    public R<Page<TaskinstVO>> pageTaskinst(Page<TaskinstVO> page, TaskinstQuery taskinstQuery) {
        return R.ok(service.pageTaskinst(page, taskinstQuery));
    }

    /**
     * 分页查询，我的已办任务
     *
     * @param page               分页对象
     * @param taskinstForMyQuery 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询")
    @GetMapping(value = "/myDoneTaskinst/page")
    public R<Page<TaskinstVO>> pageMyDoneTaskinst(Page<TaskinstVO> page, TaskinstForMyQuery taskinstForMyQuery) {
        return R.ok(service.pageMyDoneTaskinst(page, taskinstForMyQuery));
    }
}