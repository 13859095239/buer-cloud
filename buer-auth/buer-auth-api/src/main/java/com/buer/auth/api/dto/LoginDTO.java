package com.buer.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登录 DTO
 *
 * @author zoulan
 * @since 2023-06-08
 */
@Data
@Accessors(chain = true)
@Schema(description = "登录DTO")
public class LoginDTO {

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String captcha;


    /**
     * clientId
     */
    @Schema(description = "clientId")
    private String clientId;

}
