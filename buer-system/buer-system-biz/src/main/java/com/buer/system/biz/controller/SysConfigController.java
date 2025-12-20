package com.buer.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.system.api.entity.SysConfig;
import com.buer.system.api.query.ConfigQuery;
import com.buer.system.biz.service.SysConfigService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统公共参数 Controller
 *
 * @author zoulan
 * @since 2023-05-07
 */
@RestController
@RequestMapping("config")
@RequiredArgsConstructor
@Tag(name = "系统公共参数")
public class SysConfigController {

    private final SysConfigService service;

    /**
     * 通过id查询公共参数配置
     *
     * @param id id
     * @return PublicParamVO
     */
    @Operation(summary = "通过id查询公共参数配置")
    @GetMapping(value = "/{id}")
    public R<SysConfig> getConfigById(@PathVariable Long id) {
        return R.ok(service.getConfigById(id));
    }

    /**
     * 通过key查询公共参数配置
     *
     * @param configKey configKey
     * @return PublicParamVO
     */
    @Operation(summary = "通过key查询公共参数配置")
    @GetMapping(value = "/key/{configKey}")
    public R<SysConfig> getConfigByKey(@PathVariable String configKey) {
        return R.ok(service.getConfigByKey(configKey));
    }


    /**
     * 新增公共参数配置
     *
     * @param entity
     * @return Boolean
     */
    @Operation(summary = "新增公共参数配置")
    @PostMapping
    @SaCheckPermission("config-add")
    public R<Boolean> addConfig(@RequestBody SysConfig entity) {
        return R.ok(service.addConfig(entity));
    }

    /**
     * 通过id修改公共参数配置
     *
     * @param entity
     * @return Boolean
     */
    @Operation(summary = "通过id修改公共参数配置")
    @PutMapping
    @SaCheckPermission("config-edit")
    public R<Boolean> updateConfig(@RequestBody SysConfig entity) {
        return R.ok(service.updateConfig(entity));
    }

    /**
     * 通过id删除公共参数配置
     *
     * @param configIds configIds
     * @return Boolean
     */
    @SysLog("删除公共参数配置")
    @Operation(summary = "通过id删除公共参数配置")
    @DeleteMapping
    @SaCheckPermission("config-delete")
    public R<Boolean> removeConfigByIds(@RequestBody List<Long> configIds) {
        return R.ok(service.removeConfigByIds(configIds));
    }

    /**
     * 列表查询公共参数配置
     *
     * @param entity 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询公共参数配置")
    @GetMapping(value = "/list")
    public R<List<SysConfig>> listConfig(ConfigQuery entity) {
        return R.ok(service.listConfig(entity));
    }

    /**
     * 分页查询公共参数配置
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询公共参数配置")
    @GetMapping(value = "/page")
    public R<Page<SysConfig>> pageConfig(Page<SysConfig> page, ConfigQuery entity) {
        return R.ok(service.pageConfig(page, entity));
    }

    /**
     * 刷新参数缓存
     */
    @Operation(summary = "刷新参数缓存")
    @DeleteMapping("/refreshCache")
    @SaCheckPermission("config-delete")
    public R refreshCache() {
        service.resetConfigCache();
        return R.ok();
    }
}