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
     * 用户ID
     */
    @Schema(description = "用户ID")
    @ExcelProperty(value = "用户ID", index = 0)
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @ExcelProperty(value = "用户名", index = 1)
    private String username;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @ExcelProperty(value = "姓名", index = 2)
    private String nickname;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    @ExcelProperty(value = "部门名称", index = 3)
    private String deptName;

    /**
     * 性别
     */
    @Schema(description = "性别")
    @ExcelProperty(value = "性别", index = 4)
    private String gender;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @ExcelProperty(value = "手机号", index = 5)
    private String phone;

    /**
     * 办公电话
     */
    @Schema(description = "办公电话")
    @ExcelProperty(value = "办公电话", index = 6)
    private String officeNumber;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @ExcelProperty(value = "邮箱", index = 7)
    private String email;

    /**
     * 是否系统管理员
     */
    @Schema(description = "是否系统管理员")
    @ExcelProperty(value = "是否系统管理员", index = 8)
    private String adminFlag;

    /**
     * 锁定状态
     */
    @Schema(description = "锁定状态")
    @ExcelProperty(value = "锁定状态", index = 9)
    private String lockFlag;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    @ExcelProperty(value = "岗位名称", index = 10)
    private String postNames;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ExcelProperty(value = "创建时间", index = 11)
    private LocalDateTime createTime;

    /**
     * 最后修改密码时间
     */
    @Schema(description = "最后修改密码时间")
    @ExcelProperty(value = "最后修改密码时间", index = 12)
    private LocalDateTime lastChangePasswordTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @ExcelProperty(value = "备注", index = 13)
    private String remark;
}
