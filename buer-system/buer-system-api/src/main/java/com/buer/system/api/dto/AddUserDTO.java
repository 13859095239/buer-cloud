package com.buer.system.api.dto;

import com.buer.common.core.validation.annotation.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新增用户 DTO
 *
 * @author zoulan
 * @since 2023-05-07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "新增用户 DTO")
public class AddUserDTO extends UpdateUserDTO {

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @NotBlank
    private String username;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @NotBlank
    private String phone;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @Password
    @NotBlank
    private String password;

}