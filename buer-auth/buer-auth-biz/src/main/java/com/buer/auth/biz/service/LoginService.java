package com.buer.auth.biz.service;

import com.buer.auth.api.dto.LoginDTO;

/**
 * 登录 Service
 *
 * @author zoulan
 * @since 2023-05-06
 */
public interface LoginService {

    /**
     * 登录验证
     *
     * @param loginDTO 登录 DTO
     */
    Long login(LoginDTO loginDTO);

    /**
     * 注销当前登录的用户
     */
    void logout();
}
