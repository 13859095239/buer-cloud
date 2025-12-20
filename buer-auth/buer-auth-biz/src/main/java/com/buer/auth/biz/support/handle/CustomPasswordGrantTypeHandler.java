package com.buer.auth.biz.support.handle;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.oauth2.granttype.handler.PasswordGrantTypeHandler;
import cn.dev33.satoken.oauth2.granttype.handler.model.PasswordAuthResult;
import com.buer.auth.api.dto.LoginDTO;
import com.buer.auth.biz.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 自定义 Password 授权模式处理器认证过程
 * grantType = password
 * 重写默认的 password 验证
 * 详情见官网
 *
 * @author zoulan
 * @since 2025-08-11
 */
@Component
@RequiredArgsConstructor
public class CustomPasswordGrantTypeHandler extends PasswordGrantTypeHandler {

    private final LoginService loginService;

    /**
     * 根据 username、password 进行登录，如果登录失败请直接抛出异常或返回 loginId = null
     *
     * @param username 用户名
     * @param password 密码
     */
    @Override
    public PasswordAuthResult loginByUsernamePassword(String username, String password) {
        String clientId = SaHolder.getRequest()
            .getParam("client_id");
        Long userId = loginService.login(new LoginDTO().
            setUsername(username)
            .setPassword(password)
            .setClientId(clientId)
        );
        return new PasswordAuthResult(userId);
    }
}
