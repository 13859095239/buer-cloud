package com.buer.flow.biz.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Activiti 自定义 Security
 *
 * @author zoulan
 * @since 2023-05-12
 */
@Component
public class ActivitiSecurityUtil {

    private Logger logger = LoggerFactory.getLogger(ActivitiSecurityUtil.class);

    //    /**
    //     * 每次执行 activiti 的时候调用
    //     */
    //    public void logInAs() {
    //        UserForLoginVO userForLoginVO = SecurityUtils.getUserForLoginVO();
    //        SysUser user = userForLoginVO.getUser();
    //        SecurityContextHolder.setContext(new SecurityContextImpl(new Authentication() {
    //            @Override
    //            public Collection<? extends GrantedAuthority> getAuthorities() {
    //                return null;
    //            }
    //
    //            @Override
    //            public Object getCredentials() {
    //                return null;
    //            }
    //
    //            @Override
    //            public Object getDetails() {
    //                return userForLoginVO;
    //            }
    //
    //            @Override
    //            public Object getPrincipal() {
    //                return userForLoginVO;
    //            }
    //
    //            @Override
    //            public boolean isAuthenticated() {
    //                return true;
    //            }
    //
    //            @Override
    //            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    //
    //            }
    //
    //            @Override
    //            public String getName() {
    //                return user.getNickname();
    //            }
    //        }));
    //        org.activiti.engine.impl.identity.Authentication.setAuthenticatedUserId(StrUtil.toString(user.getId()));
    //    }
}
