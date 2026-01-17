package com.buer.system.api.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.buer.common.excel.annotation.ExcelDescription;
import com.buer.system.api.constants.GenderEnum;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 用户导入DTO
 * 用于Excel导入用户数据
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class UserImportDTO {

    /**
     * 用户名
     */
    @ExcelProperty(value = "用户名", index = 0)
    @ExcelDescription(value = "用户登录账号", required = true)
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度必须在2-20个字符之间")
    private String username;

    /**
     * 昵称
     */
    @ExcelProperty(value = "昵称", index = 1)
    @ExcelDescription(value = "用户的显示名称")
    @Size(max = 50, message = "昵称长度不能超过50个字符")
    private String nickname;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号", index = 2)
    @ExcelDescription(value = "11位手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱", index = 3)
    @ExcelDescription(value = "用户邮箱地址")
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", message = "邮箱格式不正确")
    private String email;

    /**
     * 部门ID
     */
    @ExcelProperty(value = "部门ID", index = 4)
    @ExcelDescription(value = "系统自动生成，无需填写")
    private Long deptId;

    /**
     * 部门名称
     */
    @ExcelProperty(value = "部门名称", index = 5)
    @ExcelDescription(value = "从下拉列表中选择")
    @Size(max = 100, message = "部门名称长度不能超过100个字符")
    private String deptName;

    /**
     * 岗位ID列表（多个用逗号分隔）
     */
    @ExcelProperty(value = "岗位ID", index = 6)
    @ExcelDescription(value = "系统自动生成，无需填写")
    private String postIds;

    /**
     * 岗位名称列表（多个用逗号分隔）
     */
    @ExcelProperty(value = "岗位名称", index = 7)
    @ExcelDescription(value = "从下拉列表中选择")
    private String postNames;

    /**
     * 性别（0-男，1-女，2-未知）
     */
    @ExcelProperty(value = "性别", index = 8)
    @ExcelDescription(value = "0-男，1-女，2-未知")
    private GenderEnum gender;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注", index = 9)
    @ExcelDescription(value = "用户备注信息")
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
}
