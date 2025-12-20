package com.buer.resource.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 返回给前端的验证码对象
 *
 * @author zoulan
 * @since 2024-09-08
 */
@Data
@Accessors(chain = true)
@Schema(description = "返回给前端的验证码对象")
public class CaptchaVO {

    /**
     * 验证码id
     */
    @Schema(description = "验证码id")
    private String captchaId;

    /**
     * 验证码图片base64
     */
    @Schema(description = "验证码图片base64")
    private String captchaImage;
}
