package com.buer.auth.biz.config;

import cn.dev33.satoken.same.SaSameUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Same-Token微服务之间共享，定时刷新
 * 详情见官网
 *
 * @author zoulan
 * @since 2023-06-08
 */
@Configuration
public class SaSameTokenRefreshTask {
    /**
     * 从0分钟开始每隔5分钟执行一次Same-Token
     */
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void refreshToken() {
        SaSameUtil.refreshToken();
    }
}