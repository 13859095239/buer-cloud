package com.buer.flow.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.flow.api.entity.ActReProcdef;
import com.buer.flow.api.query.ProcessDefinitionQuery;
import com.buer.flow.api.vo.ProcessDefinitionVO;
import com.buer.flow.biz.service.ActReProcdefService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程定义 Controller
 *
 * @author zoulan
 * @since 2024-06-09
 */
@RestController
@RequestMapping("process-definition")
@RequiredArgsConstructor
@Tag(name = "流程定义")
public class ActReProcdefController {

    private final ActReProcdefService service;

    /**
     * 通过id查询流程定义
     *
     * @param id id
     * @return ProcessDefinitionVO
     */
    @Operation(summary = "通过id查询流程定义")
    @GetMapping(value = "/{id}")
    public R<ProcessDefinitionVO> getProcessDefinitionById(@PathVariable String id) {
        return R.ok(service.getProcessDefinitionById(id));
    }

    /**
     * 通过id修改流程定义
     *
     * @param processDefinition 流程定义信息
     * @return boolean
     */
    @SysLog(value = "修改流程定义")
    @Operation(summary = "通过id修改流程定义")
    @PutMapping
    @SaCheckPermission("process-definition-edit")
    public R<Boolean> updateProcessDefinition(@RequestBody ActReProcdef processDefinition) {
        return R.ok(service.updateProcessDefinition(processDefinition));
    }

    /**
     * 通过id删除流程定义
     *
     * @param processDefinitionIds id
     * @return boolean
     */
    @SysLog(value = "删除流程定义")
    @Operation(summary = "通过id删除流程定义")
    @DeleteMapping
    @SaCheckPermission("process-definition-delete")
    public R<Boolean> removeProcessDefinitionByIds(@RequestBody List<Long> processDefinitionIds) {
        return R.ok(service.removeProcessDefinitionByIds(processDefinitionIds));
    }

    /**
     * 列表查询流程定义
     *
     * @param processDefinitionQuery 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询流程定义")
    @GetMapping(value = "/list")
    public R<List<ProcessDefinitionVO>> listProcessDefinition(ProcessDefinitionQuery processDefinitionQuery) {
        return R.ok(service.listProcessDefinition(processDefinitionQuery));
    }

    /**
     * 分页查询流程定义
     *
     * @param page                   分页对象
     * @param processDefinitionQuery 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询流程定义")
    @GetMapping(value = "/page")
    public R<Page<ProcessDefinitionVO>> pageProcessDefinition(Page<?> page, ProcessDefinitionQuery processDefinitionQuery) {
        return R.ok(service.pageProcessDefinition(page, processDefinitionQuery));
    }

    /**
     * 通过模型key获取最新流程图
     *
     * @param modelKey 流程key
     * @return 流程图
     */
    @GetMapping("/bpmn/modelKey/{modelKey}")
    public R<String> getNewestBpmnByModelKey(@PathVariable String modelKey) {
        return R.ok(service.getNewestBpmnByModelKey(modelKey));
    }

    /**
     * 通过流程定义id获取流程图
     *
     * @param procDefId 流程定义Id。
     * @return 流程图
     */
    @GetMapping("/bpmn/procDefId/{procDefId}")
    public R<String> getBpmnByProcessDefinitionId(@PathVariable String procDefId) {
        return R.ok(service.getBpmnByProcDefId(procDefId));
    }

    /**
     * 通过id更新流程定义状态
     * 激活->挂起
     *
     * @param id 流程定义id
     * @return Boolean
     */
    @PutMapping("/suspendProcDefById/{id}")
    public R<Boolean> suspendProcDefById(@PathVariable String id) {
        return R.ok(service.suspendProcDefById(id));
    }

    /**
     * 通过id更新流程定义状态
     * 挂起->激活
     *
     * @param id 流程定义id
     * @return Boolean
     */
    @PutMapping("/activateProcDefById/{id}")
    public R<Boolean> activateProcessDefinitionById(@PathVariable String id) {
        return R.ok(service.activateProcDefById(id));
    }

}