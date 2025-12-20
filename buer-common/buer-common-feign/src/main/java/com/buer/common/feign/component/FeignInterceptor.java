package com.buer.common.feign.component;

import cn.dev33.satoken.same.SaSameUtil;
import cn.dev33.satoken.stp.StpUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * feign拦截器, 在feign请求发出之前，加入一些操作
 * 按官网配置
 *
 * @author zoulan
 * @since 2023-06-12
 */
@Component
public class FeignInterceptor implements RequestInterceptor {
    /**
     * nacos读取token名称配置
     */
    @Value("${sa-token.token-name}")
    private String tokenName;

    /**
     * 为 Feign 的 RCP调用 添加请求头Same-Token
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(SaSameUtil.SAME_TOKEN, SaSameUtil.getToken());
        String tokenValue = StpUtil.getTokenValue();
        // 如果希望被调用方有会话状态，此处就还需要将 satoken 添加到请求头中
        requestTemplate.header(tokenName, "Bearer " + tokenValue);
    }
}