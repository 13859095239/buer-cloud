package com.buer.auth.biz.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.buer.auth.api.dto.LoginDTO;
import com.buer.auth.biz.service.LoginService;
import com.buer.common.core.constant.CacheConstants;
import com.buer.common.core.enums.DeviceTypeEnum;
import com.buer.common.core.enums.LoginTypeEnum;
import com.buer.common.core.entity.R;
import com.buer.common.core.exception.CheckException;
import com.buer.common.core.util.RetOps;
import com.buer.common.redis.util.RedisUtils;
import com.buer.system.api.entity.SysUser;
import com.buer.system.api.feign.RemoteUserService;
import com.buer.system.api.vo.UserForLoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * 登录 ServiceImpl
 *
 * @author zoulan
 * @since 2023-06-08
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final RemoteUserService remoteUserService;

    @Value("${security.maxRetryCount}")
    private Integer maxRetryCount;
    @Value("${security.lockTime}")
    private Integer lockTime;

    /**
     * 登录验证
     *
     * @param loginDTO 登录 DTO
     */
    @Override
    public Long login(LoginDTO loginDTO) {
        // 获取用户信息
        R<UserForLoginVO> result = remoteUserService.info(loginDTO.getUsername());
        // 用户验证
        UserForLoginVO userForLoginVO = RetOps.of(result)
            .getData()
            .orElseThrow(() -> new CheckException("用户不存在"));
        SysUser user = userForLoginVO.getUser();
        // 密码判断
        checkLogin(LoginTypeEnum.PASSWORD, user.getUsername(), () -> !BCrypt.checkpw(loginDTO.getPassword(), user.getPassword()));
        // 设置登录token
        loginByDevice(userForLoginVO, DeviceTypeEnum.PC, loginDTO.getClientId());
        return userForLoginVO.getUser()
            .getId();
    }

    /**
     * 注销当前登录的用户
     */
    @Override
    public void logout() {
        StpUtil.logout();
    }

    /**
     * 登录校验
     */
    private void checkLogin(LoginTypeEnum loginType, String username, Supplier<Boolean> supplier) {
        // 登录错误的缓存Key
        String errorKey = CacheConstants.PASSWORD_ERROR_KEY + username;
        // 获取用户登录错误次数(可自定义限制策略 例如: key + username + ip)
        Integer errorNumber = RedisUtils.getCacheObject(errorKey);
        // 锁定时间内登录 则踢出
        if (ObjectUtil.isNotNull(errorNumber) && errorNumber.equals(maxRetryCount)) {
            throw new CheckException();
        }
        // 密码错误
        if (supplier.get()) {
            // 是否第一次
            errorNumber = ObjectUtil.isNull(errorNumber) ? 1 : errorNumber + 1;
            // 达到规定错误次数 则锁定登录
            if (errorNumber >= maxRetryCount) {
                RedisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime));
                throw new CheckException(StrUtil.format("账号或密码错误，次数已超过{}次", errorNumber));
            } else {
                // 未达到规定错误次数 则递增
                RedisUtils.setCacheObject(errorKey, errorNumber);
                throw new CheckException(StrUtil.format("账号或密码错误，已经出错{}次，错误不能超过{}次，超过后会被禁用", errorNumber, maxRetryCount));
            }
        }
        // 登录成功
        else {
            // 清空错误次数
            RedisUtils.deleteObject(errorKey);
        }

    }

    /**
     * 登录系统 基于 设备类型
     * 针对相同用户体系不同设备
     *
     * @param loginUser 登录用户信息
     */
    private void loginByDevice(UserForLoginVO loginUser, DeviceTypeEnum deviceTypeEnum, String clientId) {
        SysUser user = loginUser.getUser();
        StpUtil.login(user.getId(),
            new SaLoginParameter().setDeviceType(deviceTypeEnum.getDevice())
                //   .setExtra("clientId", clientId)
                .setExtra("a", "a")
        );
        //  StpUtil.login(user.getId());
        StpUtil.getSession()
            .set(CacheConstants.LOGIN_USER_KEY, loginUser);
    }

}
