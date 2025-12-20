package com.buer.system.api.dto;

import com.buer.common.core.validation.annotation.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户重置密码 DTO
 *
 * @author zoulan
 * @since 2024-09-27
 */
@Data
@Accessors(chain = true)
@Schema(description = "用户重置密码DTO")
public class UserResetPasswordDTO implements Serializable {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long id;

    /**
     * 新密码
     */
    @Schema(description = "新密码")
    @NotBlank
    @Password
    private String newPassword;

    /**
     * 新密码确认
     */
    @Schema(description = "新密码确认")
    @NotBlank
    @Password
    private String newPasswordConfirm;

}