package com.buer.common.core.enums;

import com.buer.common.core.entity.BaseEnum;
import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用 is 标记枚举
 * 0-否,1-是
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Getter
@AllArgsConstructor
public enum IsFlagEnum implements BaseEnum<String> {

    NO("0", "否"),

    YES("1", "是");

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
