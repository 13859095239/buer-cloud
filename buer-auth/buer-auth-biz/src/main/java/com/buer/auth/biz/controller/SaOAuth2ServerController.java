package com.buer.auth.biz.controller;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.oauth2.config.SaOAuth2ServerConfig;
import cn.dev33.satoken.oauth2.processor.SaOAuth2ServerProcessor;
import cn.dev33.satoken.oauth2.strategy.SaOAuth2Strategy;
import cn.dev33.satoken.stp.StpUtil;
import com.buer.auth.api.dto.LoginDTO;
import com.buer.auth.biz.service.LoginService;
import com.buer.common.core.entity.R;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sa-Token OAuth2 Server端控制器
 * 详情见官网
 *
 * @author zoulan
 * @since 2023-06-11
 */
@RestController
@RequiredArgsConstructor
public class SaOAuth2ServerController {

    private final LoginService loginService;

    /**
     * OAuth2-Server 端：处理所有 OAuth2 相关请求
     */
    @RequestMapping("/oauth2/*")
    public Object request() {
        System.out.println("------- 进入请求: " + SaHolder.getRequest()
            .getUrl());
        return SaOAuth2ServerProcessor.instance.dister();
    }

    /**
     * Sa-OAuth2 定制化配置
     */
    @Autowired
    public void configOAuth2Server(SaOAuth2ServerConfig oauth2Server) {

        // 配置：未登录时返回的View
        SaOAuth2Strategy.instance.notLoginView = () -> "当前会话在OAuth-Server端尚未登录";

        // 配置：登录处理函数
        SaOAuth2Strategy.instance.doLoginHandle = (username, password) -> {
            String clientId = SaHolder.getRequest()
                .getParam("client_id");
            loginService.login(new LoginDTO().
                setUsername(username)
                .setPassword(password)
                .setClientId(clientId)
            );
            return R.ok();
        };

        // 重写access_token创建策略，让access_token=satoken
        // OAuth2模块生成的令牌称作资源令牌（access_token）
        // StpUtil 登录会话生成的令牌称作会话令牌（satoken）
        SaOAuth2Strategy.instance.createAccessToken = (clientId, loginId, scopes) -> {
            System.out.println("----返回会话令牌");
            return StpUtil.getOrCreateLoginSession(loginId);
        };
    }

}
