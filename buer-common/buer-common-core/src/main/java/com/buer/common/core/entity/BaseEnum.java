package com.buer.common.core.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;

/**
 * 枚举通用接口
 *
 * @author zoulan
 * @since 2025-09-26
 */
public interface BaseEnum<T> {

    /**
     * 枚举在数据库中存储的值
     *
     * @return code 枚举值
     */
    @EnumValue
    @JsonValue
    T getCode();

    /**
     * 枚举对应的描述信息，用于前端展示
     *
     * @return desc描述
     */
    String getDesc();
}