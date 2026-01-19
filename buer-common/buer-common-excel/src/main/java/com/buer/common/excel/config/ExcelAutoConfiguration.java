package com.buer.common.excel.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Excel自动配置类
 * 自动扫描并注册Excel相关的所有Bean，包括核心服务和门面类。
 *
 * @author zoulan
 * @since 2026-01-18
 */
@AutoConfiguration
@ComponentScan(basePackages = "com.buer.common.excel")
public class ExcelAutoConfiguration {
    // 通过 @ComponentScan 自动扫描并注册所有带 @Service、@Component 等注解的类
}
