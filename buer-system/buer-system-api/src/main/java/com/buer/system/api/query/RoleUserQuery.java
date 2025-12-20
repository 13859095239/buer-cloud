package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 角色用户 Query
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode()
@Schema(description = "角色用户 Query")
public class RoleUserQuery implements Serializable {

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    private String roleId;

    /**
     * 是否包含团队Id的用户
     */
    @Schema(description = "是否包含teamId的用户")
    private Boolean equalsRoleId;
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
     * 部门id
     */
    @Schema(description = "部门id")
    private Long deptId;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

}
