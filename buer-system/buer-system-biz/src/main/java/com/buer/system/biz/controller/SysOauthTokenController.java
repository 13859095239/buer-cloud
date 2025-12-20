package com.buer.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.system.api.entity.SysOauthToken;
import com.buer.system.api.query.OauthTokenQuery;
import com.buer.system.biz.service.SysOauthTokenService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 令牌 Controller
 *
 * @author zoulan
 * @since 2024-09-07
 */
@RestController
@RequestMapping("oauth-token")
@RequiredArgsConstructor
@Tag(name = "令牌")
public class SysOauthTokenController {

    private final SysOauthTokenService service;

    /**
     * 通过id删除令牌
     *
     * @param oauthTokenIds oauthTokenIds
     * @return boolean
     */
    @SysLog(value = "删除令牌")
    @Operation(summary = "通过id删除令牌")
    @DeleteMapping
    @SaCheckPermission("oauth-token-delete")
    public R<Boolean> deleteOauthTokenById(@RequestBody List<Long> oauthTokenIds) {
        return R.ok(service.removeOauthTokenByIds(oauthTokenIds));
    }

    /**
     * 列表查询令牌
     *
     * @param oauthTokenQuery 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询令牌")
    @GetMapping(value = "/list")
    public R<List<SysOauthToken>> listOauthTokens(OauthTokenQuery oauthTokenQuery) {
        return R.ok(service.listOauthToken(oauthTokenQuery));
    }

    /**
     * 分页查询令牌
     *
     * @param page            分页对象
     * @param oauthTokenQuery 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询令牌")
    @GetMapping(value = "/page")
    public R<Page<SysOauthToken>> pageOauthToken(Page<?> page, OauthTokenQuery oauthTokenQuery) {
        return R.ok(service.pageOauthToken(page, oauthTokenQuery));
    }

}