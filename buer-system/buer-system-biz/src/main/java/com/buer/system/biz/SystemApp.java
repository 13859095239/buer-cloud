package com.buer.system.biz;

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
@MapperScan("com.buer.system.biz.mapper*")
public class SystemApp {
    public static void main(String[] args) {
        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(SystemApp.class, args);
    }
}