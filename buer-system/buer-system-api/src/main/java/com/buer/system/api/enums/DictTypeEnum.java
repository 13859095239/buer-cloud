package com.buer.system.api.enums;

import com.buer.common.core.entity.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 字典类型
 *
 * @author zoulan
 * @since 2025-09-27
 */
@Getter
@RequiredArgsConstructor
public enum DictTypeEnum implements BaseEnum<String> {

    /**
     * 系统内置
     * 不可修改
     */
    SYSTEM("0", "系统内置"),

    /**
     * 业务类型
     */
    BIZ("1", "业务类");

    /**
     * 枚举值
     */
    private final String code;

    /**
     * 枚举描述
     */
    private final String desc;

}
