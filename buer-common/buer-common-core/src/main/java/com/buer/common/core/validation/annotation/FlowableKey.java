package com.buer.common.core.validation.annotation;

import com.buer.common.core.validation.validator.FlowableKeyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Flowable 模型Key 校验注解
 *
 * @author zoulan
 * @since 2025-08-18
 */
@Documented
@Constraint(validatedBy = FlowableKeyValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FlowableKey {

    /**
     * 校验失败时的默认提示信息
     */
    String message() default "流程Key不合法，需以小写字母开头，仅允许字母/数字/_/-";

    /**
     * 分组校验时使用
     */
    Class<?>[] groups() default {};

    /**
     * 负载，可以携带一些元数据
     */
    Class<? extends Payload>[] payload() default {};
}