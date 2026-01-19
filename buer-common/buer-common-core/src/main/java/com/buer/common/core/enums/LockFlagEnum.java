package com.buer.common.core.enums;

import com.buer.common.core.entity.BaseEnum;
import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 锁定状态枚举
 * 0-正常，1-锁定
 *
 * @author system
 * @since 2026-01-18
 */
@Getter
@AllArgsConstructor
public enum LockFlagEnum implements BaseEnum<String> {

    /**
     * 正常
     */
    NORMAL("0", "正常"),

    /**
     * 锁定
     */
    LOCKED("1", "锁定");

    /**
     * 枚举值
     */
    @EnumValue
    private final String code;

    /**
     * 枚举描述
     */
    private final String desc;
}
