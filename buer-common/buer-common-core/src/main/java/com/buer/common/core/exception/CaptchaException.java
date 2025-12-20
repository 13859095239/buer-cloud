package com.buer.common.core.exception;

import java.io.Serial;

/**
 * 验证码错误异常类
 *
 * @author zoulan
 * @since 2024-09-14
 */
public class CaptchaException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CaptchaException(String message) {
        super(message);
    }

}
