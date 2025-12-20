package com.buer.gateway.filter;

import cn.dev33.satoken.same.SaSameUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器，为请求添加 Same-Token
 * 按官网配置
 *
 * @author zoulan
 * @since 2023-06-12
 */
@Component
public class ForwardAuthFilter implements GlobalFilter {

    /**
     * 全局过滤器，为请求添加 Same-Token
     * 此过滤器会为 Request 请求头追加 Same-Token 参数，这个参数会被转发到子服务
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest newRequest = exchange
            .getRequest()
            .mutate()
            // 为请求追加 Same-Token 参数
            .header(SaSameUtil.SAME_TOKEN, SaSameUtil.getToken())
            .build();
        ServerWebExchange newExchange = exchange.mutate()
            .request(newRequest)
            .build();
        return chain.filter(newExchange);
    }

}