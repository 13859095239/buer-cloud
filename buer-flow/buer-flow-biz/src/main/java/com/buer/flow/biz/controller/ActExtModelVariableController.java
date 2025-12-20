package com.buer.flow.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.flow.api.entity.ActExtModelVariable;
import com.buer.flow.api.query.ModelVariableQuery;
import com.buer.flow.biz.service.ActExtModelVariableService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模型变量 Controller
 *
 * @author zoulan
 * @since 2024-05-16
 */
@RestController
@RequestMapping("model-variable")
@RequiredArgsConstructor
@Tag(name = "模型变量")
public class ActExtModelVariableController {

    private final ActExtModelVariableService service;

    /**
     * 通过id查询模型变量
     *
     * @param id id
     * @return ActExtModelVariable
     */
    @Operation(summary = "通过id查询模型变量")
    @GetMapping(value = "/{id}")
    public R<ActExtModelVariable> getModelVariableById(@PathVariable Long id) {
        return R.ok(service.getModelVariableById(id));
    }

    /**
     * 新增模型变量
     *
     * @param modelVariable 模型变量信息
     * @return boolean
     */
    @SysLog(value = "新增模型变量")
    @Operation(summary = "新增模型变量")
    @PostMapping
    @SaCheckPermission("model-add")
    public R<Boolean> addModelVariable(@RequestBody ActExtModelVariable modelVariable) {
        return R.ok(service.addModelVariable(modelVariable));
    }

    /**
     * 通过id修改模型变量
     *
     * @param modelVariable 模型变量信息
     * @return boolean
     */
    @SysLog(value = "修改模型变量")
    @Operation(summary = "通过id修改模型变量")
    @PutMapping
    @SaCheckPermission("model-edit")
    public R<Boolean> updateModelVariable(@RequestBody ActExtModelVariable modelVariable) {
        return R.ok(service.updateModelVariable(modelVariable));
    }

    /**
     * 通过id删除模型变量
     *
     * @param modelVariableIds id
     * @return boolean
     */
    @SysLog(value = "删除模型变量")
    @Operation(summary = "通过id删除模型变量")
    @DeleteMapping
    @SaCheckPermission("model-variable-edit")
    public R<Boolean> removeModelVariableByIds(@RequestBody List<Long> modelVariableIds) {
        return R.ok(service.removeModelVariableByIds(modelVariableIds));
    }

    /**
     * 列表查询模型变量
     *
     * @param modelVariableQuery 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询模型变量")
    @GetMapping(value = "/list")
    public R<List<ActExtModelVariable>> listModelVariable(ModelVariableQuery modelVariableQuery) {
        return R.ok(service.listModelVariable(modelVariableQuery));
    }

    /**
     * 分页查询模型变量
     *
     * @param page               分页对象
     * @param modelVariableQuery 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询模型变量")
    @GetMapping(value = "/page")
    public R<Page<ActExtModelVariable>> pageModelVariable(Page<ActExtModelVariable> page, ModelVariableQuery modelVariableQuery) {
        return R.ok(service.pageModelVariable(page, modelVariableQuery));
    }

}