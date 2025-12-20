package com.buer.common.security.service;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.NumberUtil;
import com.buer.common.core.constant.CacheConstants;
import com.buer.common.core.entity.R;
import com.buer.system.api.feign.RemoteRoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * sa-token 权限管理实现类
 * 详情见官网
 *
 * @author zoulan
 * @since 2023-06-09
 */
@Slf4j
@Component
@AllArgsConstructor
public class SaPermissionImpl implements StpInterface {

    private final RemoteRoleService remoteRoleService;

    /**
     * 获取：指定账号的权限码集合
     *
     * @param loginId 指定账号id
     * @return 权限集合
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 权限集合
        List<String> permissionList = new ArrayList<>();
        // 遍历角色列表，查询拥有的权限码
        for (String roleId : getRoleList(loginId, loginType)) {
            // 当前roleId在在缓存中的key
            String roleCacheKey = CacheConstants.ROLE_KEY_PREFIX + roleId;
            // 查询当前roleId缓存
            permissionList = (ArrayList<String>) SaManager.getSaTokenDao()
                    .getObject(roleCacheKey);
            if (permissionList == null) {
                // 从数据库查询这个角色所拥有的权限列表
                R<List<String>> result = remoteRoleService.getPermissionsByRoleId(NumberUtil.parseLong(roleId));
                permissionList = new ArrayList<>(result.getData()
                        .stream()
                        .map(String::valueOf)
                        .toList());
                // 当前roleId没有缓存，从数据库查询这个角色id所拥有的权限列表，更新缓存
                SaManager.getSaTokenDao()
                        .setObject(roleCacheKey, permissionList, 60 * 60 * 24 * 30);
            }
        }
        // 返回权限集合
        return permissionList;
    }

    /**
     * 获取：指定账号的角色集合
     *
     * @param loginId 指定账号id
     * @return 角色列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 获取当前登录的 session
        SaSession session = StpUtil.getSessionByLoginId(loginId);
        return session.get(CacheConstants.ROLES_KEY, () -> {
            R<List<String>> result = remoteRoleService.getRoleIdsByUserId(NumberUtil.parseLong(loginId.toString()));
            return result.getData();
        });
    }
}
