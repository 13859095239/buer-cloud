package com.buer.system.api.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.buer.common.core.enums.IsFlagEnum;
import com.buer.common.core.enums.LockFlagEnum;
import com.buer.common.excel.converter.BaseEnumConverter;
import com.buer.system.api.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户导出VO
 * 专门用于Excel导出用户数据
 *
 * @author system
 * @since 2024-01-01
 */
@Data
@Schema(description = "用户导出VO")
public class UserExportVO implements Serializable {

    /**
     * 用户ID（不导出到Excel）
     */
    @Schema(description = "用户ID")
    @ExcelIgnore
    private String id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @ExcelProperty("用户名")
    private String username;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @ExcelProperty("姓名")
    private String nickname;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    @ExcelProperty("部门名称")
    private String deptName;

    /**
     * 性别
     */
    @Schema(description = "性别")
    @ExcelProperty(value = "性别", converter = BaseEnumConverter.class)
    private GenderEnum gender;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @ExcelProperty("手机号")
    private String phone;

    /**
     * 办公电话
     */
    @Schema(description = "办公电话")
    @ExcelProperty("办公电话")
    private String officeNumber;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @ExcelProperty("邮箱")
    private String email;

    /**
     * 是否系统管理员
     */
    @Schema(description = "是否系统管理员")
    @ExcelProperty(value = "是否系统管理员", converter = BaseEnumConverter.class)
    private IsFlagEnum adminFlag;

    /**
     * 锁定状态
     */
    @Schema(description = "锁定状态")
    @ExcelProperty(value = "锁定状态", converter = BaseEnumConverter.class)
    private LockFlagEnum lockFlag;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    @ExcelProperty("岗位名称")
    private String postNames;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @ExcelProperty("角色名称")
    private String roleNames;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;
}
