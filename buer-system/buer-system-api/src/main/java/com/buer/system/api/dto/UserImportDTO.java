package com.buer.system.api.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.buer.common.excel.annotation.ExcelComment;
import com.buer.system.api.constants.GenderEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

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
    @ExcelProperty(value = {"用户信息", "用户名"}, index = 0)
    @ExcelComment(value = "用户名不能为空，长度必须在2-20个字符之间")
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度必须在2-20个字符之间")
    private String username;

    /**
     * 昵称
     */
    @ExcelProperty(value = {"用户信息", "昵称"}, index = 1)
    @ExcelComment(value = "昵称为可选字段，长度不能超过50个字符")
    @Size(max = 50, message = "昵称长度不能超过50个字符")
    private String nickname;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号", index = 2)
    @ExcelComment(value = "手机号不能为空，格式：11位数字，以1开头，第二位为3-9")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱", index = 3)
    @ExcelComment(value = "邮箱为可选字段，格式：xxx@xxx.xxx")
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", message = "邮箱格式不正确")
    private String email;

    /**
     * 部门ID
     */
    @ExcelProperty(value = "部门ID", index = 4)
    @ExcelComment(value = "部门ID为可选字段，如果填写了部门名称，则部门ID可以为空")
    private Long deptId;

    /**
     * 部门名称
     */
    @ExcelProperty(value = "部门名称", index = 5)
    @ExcelComment(value = "部门名称为可选字段，长度不能超过100个字符")
    @Size(max = 100, message = "部门名称长度不能超过100个字符")
    private String deptName;

    /**
     * 岗位ID列表（多个用逗号分隔）
     */
    @ExcelProperty(value = "岗位ID", index = 6)
    @ExcelComment(value = "岗位ID为可选字段，多个岗位ID用逗号分隔，如：1,2,3")
    private String postIds;

    /**
     * 岗位名称列表（多个用逗号分隔）
     */
    @ExcelProperty(value = "岗位名称", index = 7)
    @ExcelComment(value = "岗位名称为可选字段，多个岗位名称用逗号分隔，如：经理,主管")
    private String postNames;

    /**
     * 性别（0-男，1-女，2-未知）
     */
    @ExcelProperty(value = "性别", index = 8)
    @ExcelComment(value = "性别为可选字段，可选值：0-男，1-女，2-未知")
    private GenderEnum gender;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注", index = 9)
    @ExcelComment(value = "备注为可选字段，长度不能超过500个字符")
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
}
