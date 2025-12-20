package com.buer.system.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 当前用户基本信息 DTO
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Schema(description = "当前用户基本信息DTO")
public class MyUserDTO implements Serializable {

    /**
     * 性别
     */
    @Schema(description = "性别")
    private String gender;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @NotBlank
    private String phone;

    /**
     * 办公电话
     */
    @Schema(description = "办公电话")
    private String officeNumber;

    /**
     * email
     */
    @Schema(description = "email")
    private String email;

}