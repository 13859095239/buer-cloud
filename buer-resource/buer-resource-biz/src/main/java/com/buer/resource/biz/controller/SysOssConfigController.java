package com.buer.resource.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.resource.api.entity.SysOssConfig;
import com.buer.resource.api.query.OssConfigQuery;
import com.buer.resource.api.vo.OssConfigVO;
import com.buer.resource.biz.service.SysOssConfigService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文件配置 Controller
 *
 * @author zoulan
 * @since 2023-08-27
 */
@RestController
@RequestMapping("oss-config")
@RequiredArgsConstructor
@Tag(name = "文件配置")
public class SysOssConfigController {

    private final SysOssConfigService service;

    /**
     * 通过id查询文件配置
     *
     * @param id 文件配置id
     * @return OssConfigVO
     */
    @Operation(summary = "通过id查询文件配置")
    @GetMapping(value = "/{id}")
    public R<OssConfigVO> getOssConfigById(@PathVariable Long id) {
        return R.ok(service.getOssConfigById(id));
    }

    /**
     * 新增文件配置
     *
     * @param entity SysOssConfig
     * @return Boolean
     */
    @Operation(summary = "新增文件配置")
    @SysLog("新增文件配置")
    @PostMapping
    @SaCheckPermission("oss-config-add")
    public R<Boolean> addOssConfig(@RequestBody SysOssConfig entity) {
        return R.ok(service.addOssConfig(entity));
    }

    /**
     * 通过id修改文件配置
     *
     * @param entity SysOssConfig
     * @return Boolean
     */
    @Operation(summary = "通过id修改文件配置")
    @SysLog("修改文件配置")
    @PutMapping
    @SaCheckPermission("oss-config-edit")
    public R<Boolean> updateOssConfig(@RequestBody SysOssConfig entity) {
        return R.ok(service.updateOssConfig(entity));
    }

    /**
     * 通过id列表删除文件配置
     *
     * @param configIds configIds
     * @return Boolean
     */
    @Operation(summary = "通过id列表删除文件配置")
    @SysLog("删除文件配置")
    @DeleteMapping
    @SaCheckPermission("oss-config-delete")
    public R<Boolean> removeOssConfigByIds(@RequestBody List<Long> configIds) {
        return R.ok(service.removeOssConfigByIds(configIds));
    }

    /**
     * 列表查询文件配置
     *
     * @param entity 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询文件配置")
    @GetMapping(value = "/list")
    public R<List<OssConfigVO>> getOssConfigList(OssConfigQuery entity) {
        return R.ok(service.listOssConfig(entity));
    }

    /**
     * 分页查询文件配置
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询文件配置")
    @GetMapping(value = "/page")
    public R<Page<OssConfigVO>> getOssConfigPage(Page<OssConfigVO> page, OssConfigQuery entity) {
        return R.ok(service.pageOssConfig(page, entity));
    }

}