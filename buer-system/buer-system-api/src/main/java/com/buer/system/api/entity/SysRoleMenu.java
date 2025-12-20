package com.buer.system.api.entity;

import com.buer.common.core.entity.SuperEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 角色权限 Entity
 *
 * @author zoulan
 * @since 2021-10-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Table("sys_role_menu")
public class SysRoleMenu extends SuperEntity<SysRoleMenu> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色Id
     */
    @Id
    @Schema(description = "角色Id")
    private Long roleId;

    /**
     * 菜单Id
     */
    @Id
    @Schema(description = "菜单Id")
    private Long menuId;

}