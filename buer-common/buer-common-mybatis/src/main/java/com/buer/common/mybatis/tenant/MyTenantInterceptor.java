package com.buer.common.mybatis.tenant;

import com.buer.common.security.util.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 请求拦截器，设置tenantId
 *
 * @author zoulan
 * @since 2025-04-18
 */
public class MyTenantInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request
            , @NotNull HttpServletResponse response, @NotNull Object handler) {
        // 获取请求的当前用户tenantId
        Long tenantId = SecurityUtils.getTenantId();
        // 设置租户ID到 request 的 attribute中
        request.setAttribute("tenantId", tenantId);
        return true;
    }

}
