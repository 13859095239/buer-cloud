package com.buer.system.biz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.buer.common.security.util.PermissionUtils;
import com.buer.system.api.entity.SysRoleMenu;
import com.buer.system.biz.mapper.SysRoleMenuMapper;
import com.buer.system.biz.service.SysRoleMenuService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 角色权限 ServiceImpl
 *
 * @author zoulan
 * @since 2021-10-23
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    /**
     * 保存角色权限列表
     *
     * @param roleId  角色id
     * @param menuIds 菜单ids
     * @return Boolean
     */
    @Override
    @Transactional
    public Boolean saveRoleMenus(Long roleId, String menuIds) {
        remove(QueryWrapper.create()
            .eq(SysRoleMenu::getRoleId, roleId));
        List<SysRoleMenu> roleMenus = new ArrayList<>();
        Arrays.stream(menuIds.split(StrUtil.COMMA))
            .forEach(menuId -> {
                SysRoleMenu sysRoleMenu = new SysRoleMenu()
                    .setMenuId(Long.parseLong(menuId))
                    .setRoleId(roleId);
                roleMenus.add(sysRoleMenu);
            });
        if (!roleMenus.isEmpty()) {
            PermissionUtils.deleteRoleCache(roleId);
            return saveBatch(roleMenus);

        }
        return true;
    }
}