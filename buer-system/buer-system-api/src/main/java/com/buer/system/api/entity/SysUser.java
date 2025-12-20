package com.buer.system.api.entity;

import com.buer.common.core.entity.SuperEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 用户 Entity
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户")
@Table("sys_user")
public class SysUser extends SuperEntity<SysUser> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id
    @Schema(description = "用户ID")
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String nickname;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
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

    /**
     * 最后修改密码时间
     */
    @Schema(description = "最后修改密码时间")
    private LocalDateTime lastChangePasswordTime;

    /**
     * 0-正常，1-锁定
     */
    @Schema(description = "0-正常，1-锁定")
    private String lockFlag;

    /**
     * 0-正常，1-删除
     */
    @Schema(description = "0-正常，1-删除")
    private String delFlag;

    /**
     * 租户id
     */
    @Schema(description = "租户id")
    private Long tenantId;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Double sort;

}