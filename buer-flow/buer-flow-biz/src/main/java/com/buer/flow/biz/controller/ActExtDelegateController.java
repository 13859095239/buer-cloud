package com.buer.flow.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.flow.api.dto.DelegateDTO;
import com.buer.flow.api.query.DelegateQuery;
import com.buer.flow.api.vo.DelegateVO;
import com.buer.flow.biz.service.ActExtDelegateService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程委托 Controller
 *
 * @author zoulan
 * @since 2024-06-13
 */
@RestController
@RequestMapping("delegate")
@RequiredArgsConstructor
@Tag(name = "流程委托")
public class ActExtDelegateController {

    private final ActExtDelegateService service;

    /**
     * 通过id查询流程委托
     *
     * @param id id
     * @return DelegateVO
     */
    @Operation(summary = "通过id查询流程委托")
    @GetMapping(value = "/{id}")
    public R<DelegateVO> getDelegateById(@PathVariable Long id) {
        return R.ok(service.getDelegateById(id));
    }

    /**
     * 新增流程委托
     *
     * @param delegateDTO 流程委托信息
     * @return boolean
     */
    @SysLog(value = "新增流程委托")
    @Operation(summary = "新增流程委托")
    @PostMapping
    @SaCheckPermission("delegate-add")
    public R<Boolean> saveDelegate(@RequestBody DelegateDTO delegateDTO) {
        return R.ok(service.saveDelegate(delegateDTO));
    }

    /**
     * 通过id修改流程委托
     *
     * @param delegateDTO 流程委托信息
     * @return boolean
     */
    @SysLog(value = "修改流程委托")
    @Operation(summary = "通过id修改流程委托")
    @PutMapping
    @SaCheckPermission("delegate-edit")
    public R<Boolean> updateDelegate(@RequestBody DelegateDTO delegateDTO) {
        return R.ok(service.updateDelegate(delegateDTO));
    }

    /**
     * 通过id删除流程委托
     *
     * @param delegateIds delegateIds
     * @return boolean
     */
    @SysLog(value = "删除流程委托")
    @Operation(summary = "通过id删除流程委托")
    @DeleteMapping
    @SaCheckPermission("delegate-delete")
    public R<Boolean> removeDelegateByIds(@RequestBody List<Long> delegateIds) {
        return R.ok(service.removeDelegateByIds(delegateIds));
    }

    /**
     * 列表查询流程委托
     *
     * @param delegateQuery 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询流程委托")
    @GetMapping(value = "/list")
    public R<List<DelegateVO>> listDelegate(DelegateQuery delegateQuery) {
        return R.ok(service.listDelegate(delegateQuery));
    }

    /**
     * 分页查询流程委托
     *
     * @param page          分页对象
     * @param delegateQuery 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询流程委托")
    @GetMapping(value = "/page")
    public R<Page<DelegateVO>> pageDelegate(Page<DelegateVO> page, DelegateQuery delegateQuery) {
        return R.ok(service.pageDelegate(page, delegateQuery));
    }

}