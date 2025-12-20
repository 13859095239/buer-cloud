package com.buer.common.mybatis.config;

import com.buer.common.mybatis.tenant.MyTenantInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfiguration 配置
 * 每次请求前拦截
 *
 * @author zoulan
 * @since 2025-04-18
 */
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册租户拦截器
        registry.addInterceptor(new MyTenantInterceptor());
    }
}
