package com.buer.resource.biz.service;

import com.buer.resource.api.vo.CaptchaVO;

/**
 * 验证码处理 Service
 *
 * @author zoulan
 * @since 2024-09-10
 */

public interface CaptchaService {

    /**
     * 生成验证码
     *
     * @return 验证码信息
     */
    CaptchaVO createCaptcha();

}
