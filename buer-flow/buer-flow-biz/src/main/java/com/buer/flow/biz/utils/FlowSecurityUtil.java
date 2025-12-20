package com.buer.flow.biz.utils;

import com.buer.common.security.util.SecurityUtils;
import org.flowable.common.engine.impl.identity.Authentication;

/**
 * Flowable 权限集成
 * 因用自己的权限系统,因此有涉及倒当前用户的API,需要提前设置当前用户
 * 操作完成后记得清理
 *
 * @author zoulan
 * @since 2025-08-23
 */
public class FlowSecurityUtil {
    /**
     * 设置当前用户
     */
    public static void loginAs() {
        Authentication.setAuthenticatedUserId(SecurityUtils.getUserIdAsString());
    }

    /**
     * 设置当前用户为空,操作完成后记得清理
     */
    public static void loginOut() {
        Authentication.setAuthenticatedUserId(null);
    }
}
