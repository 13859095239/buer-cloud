package com.buer.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.system.api.entity.SysLog;
import com.buer.system.api.query.LogQuery;
import com.buer.system.api.vo.LogVO;
import com.buer.system.biz.service.SysLogService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 日志 Controller
 *
 * @author zoulan
 * @since 2023-05-11
 */
@RestController
@RequestMapping("log")
@RequiredArgsConstructor
@Tag(name = "日志")
public class SysLogController {

    private final SysLogService service;

    /**
     * 通过id查询日志
     *
     * @param id id
     * @return LogVO
     */
    @Operation(summary = "通过id查询日志")
    @GetMapping(value = "/{id}")
    public R<LogVO> getLogById(@PathVariable Long id) {
        return R.ok(service.getLogById(id));
    }

    /**
     * 新增日志
     *
     * @param entity
     * @return Boolean
     */
    @Operation(summary = "新增日志")
    @PostMapping
    public R<Boolean> addLog(@RequestBody SysLog entity) {
        return R.ok(service.addLog(entity));
    }

    /**
     * 通过id修改日志
     *
     * @param entity
     * @return Boolean
     */
    @Operation(summary = "通过id修改日志")
    @PutMapping
    public R<Boolean> updateLog(@RequestBody SysLog entity) {
        return R.ok(service.updateLog(entity));
    }

    /**
     * 通过id删除日志
     *
     * @param logIds logIds
     * @return Boolean
     */
    @Operation(summary = "通过id删除日志")
    @DeleteMapping
    @SaCheckPermission("logDelete")
    public R<Boolean> removeLogByIds(@RequestBody List<Long> logIds) {
        return R.ok(service.removeLogByIds(logIds));
    }

    /**
     * 列表查询日志
     *
     * @param entity 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询日志")
    @GetMapping(value = "/list")
    public R<List<LogVO>> listLog(LogQuery entity) {
        return R.ok(service.listLog(entity));
    }

    /**
     * 分页查询日志
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询日志")
    @GetMapping(value = "/page")
    public R<Page<LogVO>> pageLog(Page<LogVO> page, LogQuery entity) {
        return R.ok(service.pageLog(page, entity));
    }

}