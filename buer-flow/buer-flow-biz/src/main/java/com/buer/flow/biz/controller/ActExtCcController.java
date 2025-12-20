package com.buer.flow.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.flow.api.dto.CcDTO;
import com.buer.flow.api.query.CcQuery;
import com.buer.flow.api.vo.CcVO;
import com.buer.flow.biz.service.ActExtCcService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程抄送 Controller
 *
 * @author zoulan
 * @since 2024-06-13
 */
@RestController
@RequestMapping("cc")
@RequiredArgsConstructor
@Tag(name = "流程抄送")
public class ActExtCcController {

    private final ActExtCcService service;

    /**
     * 通过id查询流程抄送
     *
     * @param id id
     * @return CcVO
     */
    @Operation(summary = "通过id查询流程抄送")
    @GetMapping(value = "/{id}")
    public R<CcVO> getCcById(@PathVariable Long id) {
        return R.ok(service.getCcById(id));
    }

    /**
     * 新增流程抄送
     *
     * @param ccDTO 流程抄送信息
     * @return boolean
     */
    @SysLog(value = "新增流程抄送")
    @Operation(summary = "新增流程抄送")
    @PostMapping
    @SaCheckPermission("cc-add")
    public R<Boolean> addCc(@RequestBody CcDTO ccDTO) {
        return R.ok(service.addCc(ccDTO));
    }

    /**
     * 通过id修改流程抄送
     *
     * @param ccDTO 流程抄送信息
     * @return boolean
     */
    @SysLog(value = "修改流程抄送")
    @Operation(summary = "通过id修改流程抄送")
    @PutMapping
    @SaCheckPermission("cc-edit")
    public R<Boolean> updateCc(@RequestBody CcDTO ccDTO) {
        return R.ok(service.updateCc(ccDTO));
    }

    /**
     * 通过id删除流程抄送
     *
     * @param ccIds ccIds
     * @return boolean
     */
    @SysLog(value = "删除流程抄送")
    @Operation(summary = "通过id删除流程抄送")
    @DeleteMapping
    @SaCheckPermission("cc-delete")
    public R<Boolean> removeCcByIds(@RequestBody List<Long> ccIds) {
        return R.ok(service.removeCcByIds(ccIds));
    }

    /**
     * 列表查询流程抄送
     *
     * @param ccQuery 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询流程抄送")
    @GetMapping(value = "/list")
    public R<List<CcVO>> listCc(CcQuery ccQuery) {
        return R.ok(service.listCc(ccQuery));
    }

    /**
     * 分页查询我的待办流程抄送
     *
     * @param page    分页对象
     * @param ccQuery 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询我的待办流程抄送")
    @GetMapping(value = "/do/page")
    public R<Page<CcVO>> pageDoCc(Page<CcVO> page, CcQuery ccQuery) {
        return R.ok(service.pageDoCc(page, ccQuery));
    }

    /**
     * 分页查询我的已办流程抄送
     *
     * @param page    分页对象
     * @param ccQuery 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询我的已办流程抄送")
    @GetMapping(value = "/done/page")
    public R<Page<CcVO>> pageDoneCc(Page<CcVO> page, CcQuery ccQuery) {
        return R.ok(service.pageDoneCc(page, ccQuery));
    }

}