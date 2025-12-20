package com.buer.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.system.api.entity.SysCodeRule;
import com.buer.system.api.query.CodeRuleQuery;
import com.buer.system.api.vo.CodeRuleVO;
import com.buer.system.biz.service.SysCodeRuleService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 编号规则 Controller
 *
 * @author zoulan
 * @since 2024-06-07
 */
@RestController
@RequestMapping("code-rule")
@RequiredArgsConstructor
@Tag(name = "编号规则")
public class SysCodeRuleController {

    private final SysCodeRuleService service;

    /**
     * 通过id查询编号规则
     *
     * @param id id
     * @return CodeRuleVO
     */
    @Operation(summary = "通过id查询编号规则")
    @GetMapping(value = "/{id}")
    public R<CodeRuleVO> getCodeRuleById(@PathVariable Long id) {
        return R.ok(service.getCodeRuleById(id));
    }

    /**
     * 新增编号规则
     *
     * @param codeRule 编号规则信息
     * @return boolean
     */
    @SysLog(value = "新增编号规则")
    @Operation(summary = "新增编号规则")
    @PostMapping
    @SaCheckPermission("code-rule-add")
    public R<Boolean> addCodeRule(@RequestBody SysCodeRule codeRule) {
        return R.ok(service.addCodeRule(codeRule));
    }

    /**
     * 通过id修改编号规则
     *
     * @param codeRule 编号规则信息
     * @return boolean
     */
    @SysLog(value = "修改编号规则")
    @Operation(summary = "通过id修改编号规则")
    @PutMapping
    @SaCheckPermission("code-rule-edit")
    public R<Boolean> updateCodeRule(@RequestBody SysCodeRule codeRule) {
        return R.ok(service.updateCodeRule(codeRule));
    }

    /**
     * 通过id删除编号规则
     *
     * @param codeRuleIds codeRuleIds
     * @return boolean
     */
    @SysLog(value = "删除编号规则")
    @Operation(summary = "通过id删除编号规则")
    @DeleteMapping
    @SaCheckPermission("code-rule-delete")
    public R<Boolean> removeCodeRuleByIds(@RequestBody List<Long> codeRuleIds) {
        return R.ok(service.removeCodeRuleByIds(codeRuleIds));
    }

    /**
     * 列表查询编号规则
     *
     * @param codeRuleQuery 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询编号规则")
    @GetMapping(value = "/list")
    public R<List<CodeRuleVO>> listCodeRule(CodeRuleQuery codeRuleQuery) {
        return R.ok(service.listCodeRule(codeRuleQuery));
    }

    /**
     * 分页查询编号规则
     *
     * @param page          分页对象
     * @param codeRuleQuery 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询编号规则")
    @GetMapping(value = "/page")
    public R<Page<CodeRuleVO>> pageCodeRule(Page<CodeRuleVO> page, CodeRuleQuery codeRuleQuery) {
        return R.ok(service.pageCodeRule(page, codeRuleQuery));
    }

    /**
     * 通过编号规则key获取最新的编号
     *
     * @param key 编号规则key
     * @return 编号
     */
    @SysLog(value = "修改编号规则")
    @Operation(summary = "通过key获取最新的编号")
    @GetMapping(value = "/generateCode/key/{key}")
    @SaCheckPermission("code-rule-edit")
    public R<String> generateCodeByKey(@PathVariable String key) {
        return R.ok(service.generateCodeByKey(key));
    }


    /**
     * 通过编号规则id获取最新的编号
     *
     * @param id 编号规则id
     * @return 编号
     */
    @SysLog(value = "修改编号规则")
    @Operation(summary = "通过id获取最新的编号")
    @GetMapping(value = "/generateCode/id/{id}")
    @SaCheckPermission("code-rule-edit")
    public R<String> generateCodeById(@PathVariable Long id) {
        return R.ok(service.generateCodeById(id));
    }
}