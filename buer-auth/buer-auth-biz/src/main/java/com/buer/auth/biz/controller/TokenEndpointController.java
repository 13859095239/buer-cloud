package com.buer.auth.biz.controller;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.oauth2.SaOAuth2Manager;
import cn.dev33.satoken.oauth2.consts.SaOAuth2Consts;
import cn.dev33.satoken.oauth2.dao.SaOAuth2Dao;
import cn.dev33.satoken.oauth2.template.SaOAuth2Util;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.buer.auth.api.query.TokenEndpointQuery;
import com.buer.auth.api.vo.TokenEndpointVO;
import com.buer.common.core.entity.R;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * token 令牌 Controller
 *
 * @author zoulan
 * @since 2025-08-09
 */
@RestController
@RequestMapping("token-endpoint")
@RequiredArgsConstructor
@Tag(name = " token 令牌 Controller")
public class TokenEndpointController {
    /**
     * 令牌管理调用
     *
     * @param token token
     */
    @DeleteMapping("/{token}")
    public R<Boolean> removeTokenEndpoint(@PathVariable("token") String token) {
        SaOAuth2Util.revokeAccessToken(token);
        return R.ok();
    }

    /**
     * 分页查询  token 令牌
     *
     * @param page               分页对象
     * @param tokenEndpointQuery 查询对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R<Page<TokenEndpointVO>> pageTokenEndpoint(Page<TokenEndpointVO> page, TokenEndpointQuery tokenEndpointQuery) {
        int current = (int) page.getPageNumber();
        int size = (int) page.getPageSize();
        String username = tokenEndpointQuery.getUsername();
        SaTokenDao saTokenDao = SaManager.getSaTokenDao();
        SaOAuth2Dao saOAuth2Dao = SaOAuth2Manager.getDao();
        String accessTokenSearchKey = saOAuth2Dao.splicingAccessTokenSaveKey(StrUtil.isBlank(username) ? "*" : username);
        List<String> keyList = saTokenDao.searchData(accessTokenSearchKey, StrUtil.EMPTY, current, size, false);
        List<Map<String, String>> accessTokenModels = keyList.stream()
            .map(key -> StrUtil.removeAll(key, accessTokenSearchKey))
            .map(SaOAuth2Util::getAccessToken)
            .map(accessToken -> {
                Map<String, String> result = new HashMap<>(8);
                if (Objects.nonNull(accessToken.getLoginId())) {
                    result.put(SaOAuth2Consts.Param.username, accessToken.getLoginId()
                        .toString());
                }
                result.put("clientId", accessToken.getClientId());
                result.put("accessToken", accessToken.accessToken);
                result.put("expiresAt", DateUtil.date(accessToken.expiresTime)
                    .toString());
                return result;
            })
            .toList();
        //
        //        if (StrUtil.isNotBlank(username)) {
        // 获取用户的所有 token
        // String accessTokenSaveKey = saOAuth2Dao.splicingAccessTokenIndexKey("*", username);
        //            List<String> keyList = saTokenDao.searchData(accessTokenSaveKey, StrUtil.EMPTY, current, size, false);
        //
        //            List<Map<String, String>> accessTokenModels = keyList.stream()
        //                    .map(key -> stringRedisTemplate.opsForValue()
        //                            .get(key))
        //                    .map(SaOAuth2Util::getAccessToken)
        //                    .map(accessToken -> {
        //                        Map<String, String> result = new HashMap<>(8);
        //                        if (Objects.nonNull(accessToken.getLoginId())) {
        //                            result.put(SaOAuth2Consts.Param.username, accessToken.getLoginId()
        //                                    .toString());
        //                        }
        //                        result.put("clientId", accessToken.getClientId());
        //                        result.put("accessToken", accessToken.accessToken);
        //                        result.put("expiresAt", DateUtil.date(accessToken.expiresTime)
        //                                .toString());
        //                        return result;
        //                    })
        //                    .toList();
        //
        //            Page result = new Page(current, size);
        //            result.setRecords(accessTokenModels);
        //            result.setTotal(stringRedisTemplate.keys(accessTokenSaveKey + "*")
        //                    .size());
        //            return R.ok();
        //        }
        //
        //        // 获取 token 前缀
        //        String accessTokenSaveKey = saOAuth2Dao.splicingAccessTokenSaveKey(StrUtil.EMPTY);
        //        List<String> keyList = saTokenDao.searchData(accessTokenSaveKey,
        //                StrUtil.isBlank(username) ? StrUtil.EMPTY : username, current, size, false);
        //        List<Map<String, String>> accessTokenModels = keyList.stream()
        //                .map(key -> StrUtil.removeAll(key, accessTokenSaveKey))
        //                .map(SaOAuth2Util::getAccessToken)
        //                .map(accessToken -> {
        //                    Map<String, String> result = new HashMap<>(8);
        //                    if (Objects.nonNull(accessToken.getLoginId())) {
        //                        result.put(SaOAuth2Consts.Param.username, accessToken.getLoginId()
        //                                .toString());
        //                    }
        //                    result.put("clientId", accessToken.getClientId());
        //                    result.put("accessToken", accessToken.accessToken);
        //                    result.put("expiresAt", DateUtil.date(accessToken.expiresTime)
        //                            .toString());
        //                    return result;
        //                })
        //                .toList();
        //
        //        Page result = new Page(current, size);
        //        result.setRecords(accessTokenModels);
        //        result.setTotal(stringRedisTemplate.keys(accessTokenSaveKey + "*")
        //                .size());
        //        return R.ok(result);
        return null;
    }


}
