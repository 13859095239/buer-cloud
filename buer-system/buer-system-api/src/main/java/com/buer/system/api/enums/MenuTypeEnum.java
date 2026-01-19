package com.buer.system.api.enums;

import com.buer.common.core.entity.BaseEnum;
import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型
 *
 * @author zoulan
 * @since 2023-06-08
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum implements BaseEnum<String> {

    /**
     * 目录
     */
    PATH("0", "目录"),

    /**
     * 菜单
     */
    MENU("1", "菜单"),

    /**
     * 权限
     */
    PERMISSION("2", "权限");

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
