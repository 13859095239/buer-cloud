package com.buer.system.api.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.buer.common.excel.annotation.ExcelColumnWidth;
import com.buer.common.excel.annotation.ExcelComment;
import com.buer.system.api.enums.GenderEnum;
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
    @ExcelProperty(value =  "用户名")
    @ExcelColumnWidth(width = 20)
    @ExcelComment(value = "必填，长度必须在5-50个字符之间")
    @NotBlank(message = "用户名不能为空")
    @Size(min = 5, max = 50, message = "用户名长度必须在5-50个字符之间")
    private String username;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    @ExcelColumnWidth(width = 15)
    @ExcelComment(value = "必填，长度不能超过50个字符")
    @Size(max = 50, message = "姓名长度不能超过50个字符")
    private String nickname;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号")
    @ExcelColumnWidth(width = 15)
    @ExcelComment(value = "必填，格式：11位数字，以1开头，第二位为3-9")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    @ExcelColumnWidth(width = 25)
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", message = "邮箱格式不正确")
    private String email;

    /**
     * 部门名称
     */
    @ExcelProperty(value = "部门名称")
    @ExcelColumnWidth(width = 20)
    @ExcelComment(value = "部门名称不能为空")
    private String deptName;

    /**
     * 岗位名称列表（多个用逗号分隔）
     */
    @ExcelProperty(value = "岗位名称")
    @ExcelColumnWidth(width = 20)
    @ExcelComment(value = "多个岗位用逗号分隔，如：经理,主管")
    private String postNames;

    /**
     * 性别（0-男，1-女，2-未知）
     */
    @ExcelProperty(value = "性别")
    @ExcelColumnWidth(width = 10)
    @ExcelComment(value = "可选值：男,女")
    private GenderEnum gender;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @ExcelColumnWidth(width = 30)
    @ExcelComment(value = "长度不能超过500个字符")
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
}
