package com.buer.system.api.vo;

import com.buer.system.api.entity.SysMenu;
import com.buer.system.api.entity.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 用户详细信息 VO
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode()
@Schema(description = "用户详细信息VO")
public class UserInfoVO implements Serializable {

    /**
     * 用户基本信息
     */
    @Schema(description = "用户基本信息")
    private SysUser user;

    /**
     * 权限标识列表
     */
    @Schema(description = "权限标识集合")
    private List<String> permissions;

    /**
     * 角色对象列表
     */
    @Schema(description = "角色id列表")
    private List<Long> roles;

    /**
     * 菜单列表
     */
    @Schema(description = "菜单列表")
    private List<SysMenu> menus;

}
