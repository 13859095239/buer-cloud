package com.buer.resource.biz.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码配置
 *
 * @author zoulan
 * @since 2024-10-14
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "security.captcha")
public class CaptchaProperties {

    /**
     * 验证码类型 math 数组计算 char 字符验证
     */
    private String type;
    /**
     * line 线段干扰 circle 圆圈干扰 shear 扭曲干扰
     */
    private String category;

}
