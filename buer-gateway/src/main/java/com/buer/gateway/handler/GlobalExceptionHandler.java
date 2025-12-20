package com.buer.gateway.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 网关全局异常处理
 *
 * @author zoulan
 * @since 2024-09-17
 */
@Component
// 确保此异常处理器的优先级比默认的高
@Order(-2)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @NotNull
    @SneakyThrows
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        int code = HttpStatus.HTTP_INTERNAL_ERROR;
        String message = ex.getMessage();
        if (ex instanceof SaTokenException) {
            if (ExceptionUtil.isCausedBy(ex, NotLoginException.class)) {
                // 未能通过权限认证校验
                code = 401;
                message = "鉴权异常,未登录或登录过期！";
                logger.info("网关鉴权异常,未登录或登录过期,请求地址:{}", exchange.getRequest()
                    .getPath(), ex);
            }
        } else if (ex instanceof NotFoundException) {
            ProblemDetail problemDetail = ((NotFoundException) ex).getBody();
            code = 503;
            message = StrUtil.format("网关异常,{}", problemDetail.getDetail());
            logger.error(StrUtil.format("网关异常,错误代码:{},错误信息:{}", code, message), ex);
        } else {
            // 其他异常
            logger.error(StrUtil.format("网关异常,错误代码:{},错误信息:{}", code, message), ex);
        }
        // 错误信息
        // 格式:response.data {code,message}
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", code);
        errorResponse.put("message", message);
        return exchange.getResponse()
            .writeWith(Mono.just(exchange.getResponse()
                .bufferFactory()
                .wrap(new ObjectMapper().writeValueAsBytes(errorResponse))));
    }
}
