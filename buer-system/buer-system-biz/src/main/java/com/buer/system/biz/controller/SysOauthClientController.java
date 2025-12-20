package com.buer.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.system.api.entity.SysOauthClient;
import com.buer.system.api.query.OauthClientQuery;
import com.buer.system.biz.service.SysOauthClientService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * oauth终端 Controller
 *
 * @author zoulan
 * @since 2024-09-07
 */
@RestController
@RequestMapping("oauth-client")
@RequiredArgsConstructor
@Tag(name = "oauth终端")
public class SysOauthClientController {

    private final SysOauthClientService service;

    /**
     * 通过id查询oauth终端
     *
     * @param clientId 客户端ID
     * @return SysOauthClient
     */
    @Operation(summary = "通过id查询oauth终端")
    @GetMapping(value = "/{clientId}")
    public R<SysOauthClient> getOauthClientDetailsByClientId(@PathVariable String clientId) {
        return R.ok(service.getOauthClientDetailsByClientId(clientId));
    }

    /**
     * 新增oauth终端
     *
     * @param oauthClient oauth终端信息
     * @return boolean
     */
    @SysLog(value = "新增oauth终端")
    @Operation(summary = "新增oauth终端")
    @PostMapping
    @SaCheckPermission("oauth-client-add")
    public R<Boolean> addOauthClient(@RequestBody SysOauthClient oauthClient) {
        return R.ok(service.addOauthClient(oauthClient));
    }

    /**
     * 通过id修改oauth终端
     *
     * @param oauthClient oauth终端信息
     * @return boolean
     */
    @SysLog(value = "修改oauth终端")
    @Operation(summary = "通过id修改oauth终端")
    @PutMapping
    @SaCheckPermission("oauth-client-edit")
    public R<Boolean> updateOauthClient(@RequestBody SysOauthClient oauthClient) {
        return R.ok(service.updateOauthClient(oauthClient));
    }

    /**
     * 通过id删除oauth终端
     *
     * @param oauthClientIds oauthClientIds
     * @return boolean
     */
    @SysLog(value = "删除oauth终端")
    @Operation(summary = "通过id删除oauth终端")
    @DeleteMapping
    @SaCheckPermission("oauth-client-delete")
    public R<Boolean> removeOauthClientByIds(@RequestBody List<Long> oauthClientIds) {
        return R.ok(service.removeOauthClientByIds(oauthClientIds));
    }

    /**
     * 列表查询oauth终端
     *
     * @param oauthClientQuery 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询oauth终端")
    @GetMapping(value = "/list")
    public R<List<SysOauthClient>> listOauthClient(OauthClientQuery oauthClientQuery) {
        return R.ok(service.listOauthClient(oauthClientQuery));
    }

    /**
     * 分页查询oauth终端
     *
     * @param page             分页对象
     * @param oauthClientQuery 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询oauth终端")
    @GetMapping(value = "/page")
    public R<Page<SysOauthClient>> pageOauthClient(Page<SysOauthClient> page, OauthClientQuery oauthClientQuery) {
        return R.ok(service.pageOauthClient(page, oauthClientQuery));
    }

}