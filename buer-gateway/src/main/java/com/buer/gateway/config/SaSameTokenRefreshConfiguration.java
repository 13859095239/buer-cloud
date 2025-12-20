package com.buer.gateway.config;

import cn.dev33.satoken.same.SaSameUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * SaSameToken 刷新token配置
 *
 * @author zoulan
 * @since 2023-06-10
 */
@Configuration
public class SaSameTokenRefreshConfiguration {

    /**
     * 刷新SaSameToken
     */
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void refreshToken() {
        SaSameUtil.refreshToken();
    }

}
