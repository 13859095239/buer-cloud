package com.buer.gateway;

import com.buer.common.core.config.ControllerAdviceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * gateway启动类
 * gateway采用webFlux,与mvc冲突,排除mvc有关的配置
 *
 * @author zoulan
 * @since 2023-06-08
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(exclude = {ControllerAdviceConfiguration.class})
public class GatewayApp {
    public static void main(String[] args) {
        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(GatewayApp.class, args);
    }
}