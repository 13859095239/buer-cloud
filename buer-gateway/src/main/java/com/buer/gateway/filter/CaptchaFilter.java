package com.buer.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.buer.common.core.constant.CacheConstants;
import com.buer.common.core.exception.CaptchaException;
import com.buer.common.redis.util.RedisUtils;
import com.buer.gateway.config.properties.CaptchaProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 验证码过滤器
 * 验证码验证网关上统一处理，不在业务模块处理
 * 验证码生成在resource服务
 *
 * @author zoulan
 * @since 2024-09-10
 */
@Component
@RequiredArgsConstructor
public class CaptchaFilter implements GlobalFilter {
    private final static String[] VALIDATE_URL = new String[]{"/oauth2/token"};
    // 验证码配置
    private final CaptchaProperties captcha;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 非登录/注册请求或验证码关闭，不处理
        if (captcha.getEnabled() && StringUtils.equalsAnyIgnoreCase(request.getURI()
            .getPath(), VALIDATE_URL)) {
            String captchaId = request.getQueryParams()
                .getFirst("captchaId");
            String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + captchaId;
            String captchaValue = request.getQueryParams()
                .getFirst("captchaValue");
            if (StrUtil.isBlank(captchaId) || StrUtil.isBlank(captchaValue)) {
                throw new CaptchaException("验证码不能为空");
            }
            // 获取验证码
            String storedCaptcha = RedisUtils.getObject(verifyKey);
            // 验证码判断
            if (StrUtil.isBlank(storedCaptcha)) {
                throw new CaptchaException("验证码已失效");
            }
            // 验证码匹配
            if (!storedCaptcha.equalsIgnoreCase(captchaValue)) {
                throw new CaptchaException("验证码错误");
            }
            // 验证成功，删除验证码
            RedisUtils.deleteObject(verifyKey);
        }
        return chain.filter(exchange);
    }
}
