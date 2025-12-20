package com.buer.auth.biz;

import cn.dev33.satoken.oauth2.SaOAuth2Manager;
import com.buer.common.feign.annotation.EnableCommonFeignClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 *
 * @author zoulan
 * @since 2023-06-08
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCommonFeignClients
@ComponentScan({"com.buer"})
@Slf4j
public class AuthApp {
    public static void main(String[] args) {
        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(AuthApp.class, args);
        System.out.println("Sa-Token-OAuth2 Server端启动成功，配置如下：");
        System.out.println(SaOAuth2Manager.getServerConfig());
    }
}