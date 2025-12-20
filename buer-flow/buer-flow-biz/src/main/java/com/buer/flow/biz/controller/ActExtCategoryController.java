package com.buer.flow.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.flow.api.entity.ActExtCategory;
import com.buer.flow.api.query.ModelCategoryQuery;
import com.buer.flow.biz.service.ActExtCategoryService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程类型 Controller
 *
 * @author zoulan
 * @since 2024-05-16
 */
@RestController
@RequestMapping("category")
@RequiredArgsConstructor
@Tag(name = "流程类型")
public class ActExtCategoryController {

    private final ActExtCategoryService service;

    /**
     * 通过id查询流程类型
     *
     * @param id id
     * @return ActExtCategory
     */
    @Operation(summary = "通过id查询流程类型")
    @GetMapping(value = "/{id}")
    public R<ActExtCategory> getModelCategoryById(@PathVariable Long id) {
        return R.ok(service.getModelCategoryById(id));
    }


    /**
     * 新增流程类型
     *
     * @param modelCategory 流程类型信息
     * @return boolean
     */
    @SysLog(value = "新增流程类型")
    @Operation(summary = "新增流程类型")
    @PostMapping(consumes = "application/json")
    @SaCheckPermission("category-add")
    public R<Boolean> addModelCategory(@RequestBody ActExtCategory modelCategory) {
        return R.ok(service.addModelCategory(modelCategory));
    }

    /**
     * 通过id修改流程类型
     *
     * @param modelCategory 流程类型信息
     * @return boolean
     */
    @SysLog(value = "修改流程类型")
    @Operation(summary = "通过id修改流程类型")
    @PutMapping
    @SaCheckPermission("category-edit")
    public R<Boolean> updateModelCategory(@RequestBody ActExtCategory modelCategory) {
        return R.ok(service.updateModelCategory(modelCategory));
    }

    /**
     * 通过id删除流程类型
     *
     * @param modelCategoryIds ids
     * @return boolean
     */
    @SysLog(value = "删除流程类型")
    @Operation(summary = "通过id删除流程类型")
    @DeleteMapping
    @SaCheckPermission("category-delete")
    public R<Boolean> removeModelCategoryByIds(@RequestBody List<Long> modelCategoryIds) {
        return R.ok(service.removeModelCategoryByIds(modelCategoryIds));
    }

    /**
     * 列表查询流程类型
     *
     * @param modelCategoryQuery 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询流程类型")
    @GetMapping(value = "/list")
    public R<List<ActExtCategory>> listModelCategory(ModelCategoryQuery modelCategoryQuery) {
        return R.ok(service.listModelCategory(modelCategoryQuery));
    }

    /**
     * 分页查询流程类型
     *
     * @param page               分页对象
     * @param modelCategoryQuery 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询流程类型")
    @GetMapping(value = "/page")
    public R<Page<ActExtCategory>> pageModelCategory(Page<ActExtCategory> page, ModelCategoryQuery modelCategoryQuery) {
        return R.ok(service.pageModelCategory(page, modelCategoryQuery));
    }

}