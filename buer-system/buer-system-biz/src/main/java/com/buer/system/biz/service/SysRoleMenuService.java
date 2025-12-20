package com.buer.system.biz.service;

import com.buer.system.api.entity.SysRoleMenu;
import com.mybatisflex.core.service.IService;

/**
 * 角色权限 Service
 *
 * @author zoulan
 * @since 2021-10-23
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 保存角色权限列表
     *
     * @param roleId  角色id
     * @param menuIds 菜单ids
     * @return Boolean
     */
    Boolean saveRoleMenus(Long roleId, String menuIds);

}