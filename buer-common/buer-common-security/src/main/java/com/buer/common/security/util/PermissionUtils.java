package com.buer.common.security.util;

import cn.dev33.satoken.session.SaSessionCustomUtil;
import com.buer.common.core.constant.CacheConstants;

/**
 * Sa-Token 权限工具类
 *
 * @author zoulan
 * @since 2023-06-12
 */
public class PermissionUtils {

    /**
     * 删除角色下的权限缓存
     */
    public static void deleteRoleCache(long role) {
        SaSessionCustomUtil.deleteSessionById(CacheConstants.ROLE_KEY_PREFIX + role);
    }
}
