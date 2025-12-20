package com.buer.system.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 更新用户 DTO
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Schema(description = "更新用户 DTO")
public class UpdateUserDTO implements Serializable {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @NotBlank
    private String username;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @NotBlank
    private String nickname;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    @NotNull
    private Long deptId;

    /**
     * 注册地区ID
     */
    @Schema(description = "注册地区ID")
    private Long areaId;

    /**
     * 是否系统管理员
     */
    @Schema(description = "是否系统管理员")
    private String adminFlag;

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
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 最后修改密码时间
     */
    @Schema(description = "最后修改密码时间")
    private LocalDateTime lastChangePasswordTime;

    /**
     * 0-正常，1-锁定
     */
    @Schema(description = "0-正常，1-锁定")
    @NotBlank
    private String lockFlag;

    /**
     * 排序
     */
    @Schema(description = "排序")
    @NotNull
    private Double sort;

    /**
     * 岗位信息
     */
    @Schema(description = "岗位信息")
    private String postIds;

}