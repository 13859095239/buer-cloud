package com.buer.common.security.util;

import cn.dev33.satoken.stp.StpUtil;
import com.buer.common.core.constant.CacheConstants;
import com.buer.system.api.entity.SysUser;
import com.buer.system.api.vo.UserForLoginVO;
import lombok.experimental.UtilityClass;

/**
 * Sa-Token 安全工具类
 *
 * @author zoulan
 * @since 2023-06-12
 */
@UtilityClass
public class SecurityUtils {


    /**
     * 获取用户权限对象
     */
    public UserForLoginVO getUserForLoginVO() {
        UserForLoginVO userForLoginVO = (UserForLoginVO) StpUtil.getSession()
                .get(CacheConstants.LOGIN_USER_KEY);
        return userForLoginVO;
    }

    /**
     * 获取用户
     */
    public SysUser getUser() {
        if (!StpUtil.isLogin()) {
            return null;
        }
        UserForLoginVO vo = getUserForLoginVO();
        SysUser user = vo.getUser();
        return user;
    }

    /**
     * 获取用户id
     */
    public Long getUserId() {
        if (!StpUtil.isLogin()) {
            return null;
        }
        return StpUtil.getLoginIdAsLong();
    }

    /**
     * 获取用户id
     */
    public String getUserIdAsString() {
        if (!StpUtil.isLogin()) {
            return null;
        }
        String userId = StpUtil.getLoginIdAsString();
        return userId;
    }

    /**
     * 获取租户id
     */
    public Long getTenantId() {
        if (!StpUtil.isLogin()) {
            return null;
        }
        return getUser().getTenantId();
    }

}
