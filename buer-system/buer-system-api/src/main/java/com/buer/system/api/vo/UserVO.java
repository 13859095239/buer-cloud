package com.buer.system.api.vo;

import cn.hutool.core.util.DesensitizedUtil;
import com.buer.common.sensitive.annotation.Sensitive;
import com.mybatisflex.annotation.RelationOneToOne;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户 VO
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Schema(description = "用户")
public class UserVO implements Serializable {

    /**
     * 用户ID
     */
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
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;
    /**
     * 部门名称
     */
    @RelationOneToOne(selfField = "deptId", targetTable = "sys_dept", targetField = "id", valueField = "name")
    @Schema(description = "部门")
    private String deptName;

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
    @Sensitive(desensitizedType = DesensitizedUtil.DesensitizedType.MOBILE_PHONE)
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
     * 排序
     */
    @Schema(description = "排序")
    private Object sort;

    /**
     * 岗位id列表
     */
    @Schema(description = "岗位id列表")
    private String postIds;
    /**
     * 岗位名称列表
     */
    @Schema(description = "岗位名称列表")
    private String postNames;
}