package com.buer.flow.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import com.buer.common.core.entity.R;
import com.buer.common.security.util.SecurityUtils;
import com.buer.flow.api.dto.CreateProcinstDTO;
import com.buer.flow.api.entity.ActHiProcinst;
import com.buer.flow.api.query.ProcinstForMyQuery;
import com.buer.flow.api.query.ProcinstQuery;
import com.buer.flow.api.vo.CreateProcinstInfoVO;
import com.buer.flow.api.vo.CreatedProcinstVO;
import com.buer.flow.api.vo.ProcinstInfoVO;
import com.buer.flow.api.vo.ProcinstVO;
import com.buer.flow.biz.service.ActHiProcinstService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程实例 Controller
 *
 * @author zoulan
 * @since 2023-05-11
 */
@RestController
@RequestMapping("procinst")
@RequiredArgsConstructor
@Tag(name = "流程实例 Controller")
public class ActHiProcinstController {

    private final ActHiProcinstService service;

    /**
     * 通过id查询流程实例
     *
     * @param id id
     * @return ProcinstVO
     */
    @Operation(summary = "通过id查询流程实例")
    @GetMapping(value = "/{id}")
    public R<ProcinstVO> getProcinstById(@PathVariable String id) {
        return R.ok(service.getProcinstById(id));
    }

    /**
     * 通过模型key获取新建流程实例信息
     *
     * @param modelKey 模型key
     * @return 流程实例信息 VO
     */
    @Operation(summary = "通过模型key获取新建流程实例信息")
    @GetMapping(value = "/createProcinstInfo/{modelKey}")
    public R<CreateProcinstInfoVO> getCreateProcinstInfo(@PathVariable String modelKey) {
        return R.ok(service.getCreateProcinstInfo(modelKey));
    }

    /**
     * 通过流程实例ID获取流程信息
     *
     * @param procinstId 流程实例id
     * @return 流程实例信息 VO
     */
    @Operation(summary = "通过流程实例id获取流程信息")
    @GetMapping(value = "/viewProcinstInfo/{procinstId}")
    public R<ProcinstInfoVO> getProcinstInfoById(@PathVariable String procinstId) {
        return R.ok(service.getProcinstInfoById(procinstId));
    }

    /**
     * 通过id修改流程实例
     *
     * @param procinst 流程实例对象
     * @return Boolean
     */
    @Operation(summary = "通过id修改流程实例")
    @PutMapping
    public R<Boolean> updateProcinst(@RequestBody ActHiProcinst procinst) {
        return R.ok(service.updateProcinst(procinst));
    }

    /**
     * 通过id删除流程实例
     *
     * @param procinstIds procinstIds
     * @return Boolean
     */
    @Operation(summary = "通过id删除流程实例")
    @DeleteMapping
    public R<Boolean> removeProcinstByIds(@RequestBody List<String> procinstIds) {
        return R.ok(service.removeProcinstByIds(procinstIds));
    }

    /**
     * 列表查询流程实例
     *
     * @param entity 查询对象流程实例
     * @return 列表
     */
    @Operation(summary = "列表查询流程实例")
    @GetMapping(value = "/list")
    public R<List<ProcinstVO>> listProcinst(ProcinstQuery entity) {
        return R.ok(service.listProcinst(entity));
    }

    /**
     * 分页查询流程实例
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询流程实例")
    @GetMapping(value = "/page")
    public R<Page<ProcinstVO>> pageProcinst(Page<ProcinstVO> page, ProcinstQuery entity) {
        return R.ok(service.pageProcinst(page, entity));
    }

    /**
     * 分页查询,我的流程实例
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询")
    @GetMapping(value = "/my-created-procinst/page")
    public R<Page<ProcinstVO>> getMyCreatedProcinstPage(Page<ProcinstVO> page, ProcinstForMyQuery entity) {
        ProcinstQuery procinstQuery = new ProcinstQuery()
            .setAssignee(SecurityUtils.getUserId()
                .toString());
        BeanUtil.copyProperties(entity, procinstQuery);
        return R.ok(service.pageProcinst(page, procinstQuery));
    }

    /**
     * 创建流程实例
     *
     * @param createProcinstDTO 创建流程实例对象
     * @return CreatedProcinstVO
     */
    @Operation(summary = "创建流程实例")
    @PostMapping("/createProcinst")
    public R<CreatedProcinstVO> createProcinst(@RequestBody CreateProcinstDTO createProcinstDTO) {
        return R.ok(service.createProcinst(createProcinstDTO));
    }

    /**
     * 激活流程实例
     *
     * @param procInstId 流程实例id
     * @return Boolean
     */
    @Operation(summary = "激活流程实例")
    @PostMapping("/activateProcInstById/{procInstId}")
    public R<Boolean> activateProcInstById(@PathVariable String procInstId) {
        return R.ok(service.activateProcinstById(procInstId));
    }

    /**
     * 挂起流程实例
     *
     * @param procInstId 流程实例id
     * @return Boolean
     */
    @Operation(summary = "挂起流程实例")
    @PostMapping("/suspendProcInstById/{procInstId}")
    public R<Boolean> suspendProcInstById(@PathVariable String procInstId) {
        return R.ok(service.suspendProcinstById(procInstId));
    }

}