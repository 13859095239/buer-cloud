package com.buer.auth.biz.controller;

import com.buer.auth.biz.service.LoginService;
import com.buer.common.core.entity.R;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * token Controller
 *
 * @author zoulan
 * @since 2023-06-07
 */
@RestController
@RequestMapping("login")
@RequiredArgsConstructor
@Tag(name = "login")
public class LoginController {

    private final LoginService loginService;

    /**
     * 注销当前登录用户
     */
    @DeleteMapping("logout")
    public R<Void> logout() {
        loginService.logout();
        return R.ok();
    }

}
