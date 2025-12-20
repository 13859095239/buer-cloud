package com.buer.resource.biz;

import com.buer.common.feign.annotation.EnableCommonFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动类
 *
 * @author zoulan
 * @since 2023-08-27
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCommonFeignClients
@MapperScan("com.buer.resource.biz.mapper*")
public class ResourceApp {
    public static void main(String[] args) {
        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(ResourceApp.class, args);
    }
}