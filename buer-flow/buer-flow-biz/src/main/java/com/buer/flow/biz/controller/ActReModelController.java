package com.buer.flow.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.flow.api.dto.EditorXmlDTO;
import com.buer.flow.api.dto.ModelAddDTO;
import com.buer.flow.api.dto.ModelUpdateDTO;
import com.buer.flow.api.query.ModelQuery;
import com.buer.flow.api.vo.ModelVO;
import com.buer.flow.biz.service.ActReModelService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模型 Controller
 *
 * @author zoulan
 * @since 2023-05-11
 */
@RestController
@RequestMapping("model")
@RequiredArgsConstructor
@Tag(name = "模型")
public class ActReModelController {

    private final ActReModelService service;

    /**
     * 通过id查询模型
     *
     * @param id id
     * @return ModelVO
     */
    @Operation(summary = "通过id查询模型")
    @GetMapping(value = "/{id}")
    public R<ModelVO> getModelById(@PathVariable String id) {
        return R.ok(service.getModelById(id));
    }

    /**
     * 通过key查询模型
     *
     * @param key 模型key
     * @return ModelVO
     */
    @Operation(summary = "通过模型key查询模型")
    @GetMapping(value = "/key/{key}")
    public R<ModelVO> getModelByKey(@PathVariable String key) {
        return R.ok(service.getModelByKey(key));
    }

    /**
     * 新增模型
     *
     * @param modelAddDTO 模型对象
     * @return Boolean
     */
    @Operation(summary = "新增模型")
    @PostMapping
    @SaCheckPermission("model-add")
    public R<String> saveModel(@Valid @RequestBody ModelAddDTO modelAddDTO) {
        return R.ok(service.saveModel(modelAddDTO));
    }

    /**
     * 通过id修改模型
     *
     * @param modelUpdateDTO 模型对象
     * @return Boolean
     */
    @Operation(summary = "通过id修改模型")
    @PutMapping
    @SaCheckPermission("model-edit")
    public R<Boolean> updateModelById(@RequestBody ModelUpdateDTO modelUpdateDTO) {
        return R.ok(service.updateModelById(modelUpdateDTO));
    }

    /**
     * 通过id删除模型
     *
     * @param modelIds id
     * @return Boolean
     */
    @Operation(summary = "通过id删除模型")
    @DeleteMapping
    @SaCheckPermission("model-delete")
    public R<Boolean> removeModelByIds(@RequestBody List<String> modelIds) {
        return R.ok(service.removeModelByIds(modelIds));
    }

    /**
     * 列表查询模型
     *
     * @param entity 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询模型")
    @GetMapping(value = "/list")
    public R<List<ModelVO>> listModel(ModelQuery entity) {
        return R.ok(service.listModel(entity));
    }

    /**
     * 通过id查询模型xml
     *
     * @param id 模型id
     * @return 模型xml
     */
    @Operation(summary = "通过id查询模型xml")
    @GetMapping(value = "bpmn/{id}")
    public R<String> getModelBpmn(@PathVariable String id) {
        return R.ok(service.getModelBpmn(id));
    }

    /**
     * 更新模型xml
     *
     * @param editorXmlDTO 更新模型XML
     * @return Boolean
     */
    @PutMapping(value = "bpmn")
    public R<Boolean> updateModelEditorBpmn(@RequestBody EditorXmlDTO editorXmlDTO) {
        return R.ok(service.updateModelEditorBpmn(editorXmlDTO));
    }

    /**
     * 分页查询
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询")
    @GetMapping(value = "/page")
    public R<Page<ModelVO>> pageModel(Page<ModelVO> page, ModelQuery entity) {
        return R.ok(service.pageModel(page, entity));
    }

    /**
     * 通过模型id,发布流程
     *
     * @param modelId 模型id
     * @return R
     */
    @PostMapping("/deploy/modelId/{modelId}")
    public R<Void> deployModelByModelId(@PathVariable String modelId) {
        service.deployModel(modelId);
        return R.ok();
    }

    /**
     * 通过流程定义id，发布为当前版本
     *
     * @param procDefId 流程定义id
     * @return Boolean
     */
    @PostMapping("deploy/procDefId/{procDefId}")
    public R<Boolean> deployModelByProcDefId(@PathVariable String procDefId) {
        return R.ok(service.deployModelByProcDefId(procDefId));
    }

}