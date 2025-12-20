package com.buer.common.mybatis.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 自动生成Java代码类型
 *
 * @author zoulan
 * @since 2023-06-08
 */
@Getter
@RequiredArgsConstructor
public enum JavaCodeTypeEnum {

    /**
     * 增删改查代码
     */
    CURD("curd"),

    /**
     * 空白代码
     */
    BLANK("blank"),

    /**
     * 树的代码，包含curd
     */
    TREE_CURD("treeCurd");

    private final String javaCodeType;
}