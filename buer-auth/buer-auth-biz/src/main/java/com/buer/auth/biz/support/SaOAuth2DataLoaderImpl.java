package com.buer.auth.biz.support;

import cn.dev33.satoken.oauth2.data.loader.SaOAuth2DataLoader;
import cn.dev33.satoken.oauth2.data.model.loader.SaClientModel;
import cn.hutool.core.lang.Opt;
import com.buer.common.core.entity.R;
import com.buer.system.api.entity.SysOauthClient;
import com.buer.system.api.feign.RemoteOauthClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Sa-Token OAuth2自定义数据加载器
 * 详情见官网
 *
 * @author zoulan
 * @since 2023-06-08
 */
@Component
@RequiredArgsConstructor
public class SaOAuth2DataLoaderImpl implements SaOAuth2DataLoader {

    private final RemoteOauthClientService remoteOauthClientService;

    // 根据clientId获取Client信息
    @Override
    public SaClientModel getClientModel(String clientId) {
        R<SysOauthClient> result = remoteOauthClientService.getOauthClientDetailsByClientId(clientId);
        SysOauthClient client = Opt.ofBlankAble(result.getData())
            .get();
        if (client == null) {
            return null;
        }
        return new SaClientModel()
            // client id
            .setClientId(clientId)
            // client 秘钥
            .setClientSecret(client.getClientSecret())
            // 添加应用允许授权的所有 redirect_uri
            .addAllowRedirectUris(Opt.ofBlankAble(client.getWebServerRedirectUris())
                .orElse("")
                .split(","))
            // 添加应用签约的所有权限
            .addContractScopes(Opt.ofBlankAble(client.getScopes())
                .orElse("")
                .split(","))
            // 应用允许的所有授权模式 grant_type
            .addAllowGrantTypes(Opt.ofBlankAble(client.getAuthorizedGrantTypes())
                .orElse("")
                .split(","));
    }

    // 根据 clientId 和 loginId 获取 openid
    @Override
    public String getOpenid(String clientId, Object loginId) {
        // 此处使用框架默认算法生成 openid，真实环境建议改为从数据库查询
        return SaOAuth2DataLoader.super.getOpenid(clientId, loginId);
    }

}