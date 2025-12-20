package com.buer.common.core.validation.validator;

import cn.hutool.core.util.StrUtil;
import com.buer.common.core.validation.annotation.FlowableKey;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * FlowableKey 注解对应的校验器
 * - 必须以小写字母开头
 * - 后续仅允许字母、数字、下划线、短横线
 *
 * @author zoulan
 * @since 2025-08-18
 */
public class FlowableKeyValidator implements ConstraintValidator<FlowableKey, String> {
    private static final String REGEX = "^[a-z][a-zA-Z0-9_-]*$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 空值直接判定为合法
        if (StrUtil.isBlank(value)) {
            return true;
        }
        return value.matches(REGEX);
    }
}