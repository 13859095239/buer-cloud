package com.buer.system.api.constants;

import com.buer.common.core.entity.BaseEnum;
import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别
 *
 * @author zoulan
 * @since 2026-01-07
 */
@Getter
@AllArgsConstructor
public enum GenderEnum implements BaseEnum<String> {

    /**
     * 目录
     */
    MAN("0", "男"),

    /**
     * 菜单
     */
    WOMAN("1", "女");

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
