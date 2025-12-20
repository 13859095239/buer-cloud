package com.buer.common.core.validation.annotation;

import com.buer.common.core.validation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 弱密码校验注解
 *
 * @author zoulan
 * @since 2025-09-21
 */
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    /**
     * 校验失败时的默认提示信息
     */
    String message() default "密码不合法，需长度在8-20位之间，且包含大小写字母和数字";

    /**
     * 分组校验时使用
     */
    Class<?>[] groups() default {};

    /**
     * 负载，可以携带一些元数据
     */
    Class<? extends Payload>[] payload() default {};
}