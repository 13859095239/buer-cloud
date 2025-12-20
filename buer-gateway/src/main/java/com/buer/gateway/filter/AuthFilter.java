package com.buer.gateway.filter;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.buer.gateway.config.properties.IgnoreWhiteProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sa-Token 权限认证 拦截器
 *
 * @author zoulan
 * @since 2023-06-12
 */
@Configuration
public class AuthFilter {
    /**
     * 注册 Sa-Token 全局过滤器
     */
    @Bean
    public SaReactorFilter getSaReactorFilter(IgnoreWhiteProperties ignoreWhite) {
        return new SaReactorFilter()
            // 拦截地址，拦截全部path
            .addInclude("/**")
            // 开放地址
            .addExclude("/favicon.ico", "/actuator/**")
            // 鉴权方法：每次访问进入
            .setAuth(obj -> {
                // 登录校验 -- 拦截所有路由
                SaRouter.match("/**")
                    .notMatch(ignoreWhite.getWhites())
                    .check(r -> {
                        // 检查是否登录 是否有token
                        StpUtil.checkLogin();
                    });
            });
        //                // 异常处理方法：每次setAuth函数出现异常时进入
        //                .setError(e ->
        //                        SaResult.error("认证失败，无法访问系统资源")
        //                        .setCode(HttpStatus.HTTP_UNAUTHORIZED)
        //                );
    }
}
