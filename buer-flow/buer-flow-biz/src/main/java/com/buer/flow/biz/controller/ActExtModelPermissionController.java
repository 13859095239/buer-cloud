package com.buer.flow.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.flow.api.entity.ActExtModelPermission;
import com.buer.flow.api.query.ModelPermissionQuery;
import com.buer.flow.biz.service.ActExtModelPermissionService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模型权限 Controller
 *
 * @author zoulan
 * @since 2024-05-16
 */
@RestController
@RequestMapping("model-permission")
@RequiredArgsConstructor
@Tag(name = "模型权限")
public class ActExtModelPermissionController {

    private final ActExtModelPermissionService service;

    /**
     * 通过id查询模型权限
     *
     * @param id id
     * @return ActExtModelPermission
     */
    @Operation(summary = "通过id查询模型权限")
    @GetMapping(value = "/{id}")
    public R<ActExtModelPermission> getModelPermissionById(@PathVariable Long id) {
        return R.ok(service.getModelPermissionById(id));
    }

    /**
     * 新增模型权限
     *
     * @param modelPermission 模型权限信息
     * @return boolean
     */
    @SysLog(value = "新增模型权限")
    @Operation(summary = "新增模型权限")
    @PostMapping
    @SaCheckPermission("model-add")
    public R<Boolean> addModelPermission(@RequestBody ActExtModelPermission modelPermission) {
        return R.ok(service.addModelPermission(modelPermission));
    }

    /**
     * 通过id修改模型权限
     *
     * @param modelPermission 模型权限信息
     * @return boolean
     */
    @SysLog(value = "修改模型权限")
    @Operation(summary = "通过id修改模型权限")
    @PutMapping
    @SaCheckPermission("model-edit")
    public R<Boolean> updateModelPermission(@RequestBody ActExtModelPermission modelPermission) {
        return R.ok(service.updateModelPermission(modelPermission));
    }

    /**
     * 通过id删除模型权限
     *
     * @param modelPermissionIds modelPermissionIds
     * @return boolean
     */
    @SysLog(value = "删除模型权限")
    @Operation(summary = "通过id删除模型权限")
    @DeleteMapping
    @SaCheckPermission("model-edit")
    public R<Boolean> removeModelPermissionByIds(@RequestBody List<Long> modelPermissionIds) {
        return R.ok(service.removeModelPermissionByIds(modelPermissionIds));
    }

    /**
     * 列表查询模型权限
     *
     * @param modelPermissionQuery 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询模型权限")
    @GetMapping(value = "/list")
    public R<List<ActExtModelPermission>> listModelPermission(ModelPermissionQuery modelPermissionQuery) {
        return R.ok(service.listModelPermission(modelPermissionQuery));
    }

    /**
     * 分页查询模型权限
     *
     * @param page                 分页对象
     * @param modelPermissionQuery 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询模型权限")
    @GetMapping(value = "/page")
    public R<Page<ActExtModelPermission>> pageModelPermission(Page<ActExtModelPermission> page, ModelPermissionQuery modelPermissionQuery) {
        return R.ok(service.pageModelPermission(page, modelPermissionQuery));
    }

}