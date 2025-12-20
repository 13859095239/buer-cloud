package com.buer.flow.biz;

import com.buer.common.feign.annotation.EnableCommonFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动类
 *
 * @author zoulan
 * @since 2023-06-08
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCommonFeignClients
@MapperScan("com.buer.flow.biz.mapper*")
public class FlowApp {
    public static void main(String[] args) {
        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(FlowApp.class, args);
    }
}