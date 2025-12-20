package com.buer.visual.monitor;

import com.buer.common.feign.annotation.EnableCommonFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 
 * 启动类
 
 *
 * @author zoulan
 * @since 2024-11-02
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCommonFeignClients
public class VisualMonitorApp {
    public static void main(String[] args) {
        SpringApplication.run(VisualMonitorApp.class, args);
    }
}
