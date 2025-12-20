package com.buer.system.api.vo;

import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelProperty("性别")
    private String gender;

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
    @ExcelProperty("是否系统管理员")
    private String adminFlag;

    /**
     * 锁定状态
     */
    @Schema(description = "锁定状态")
    @ExcelProperty("锁定状态")
    private String lockFlag;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    @ExcelProperty("岗位名称")
    private String postNames;

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
