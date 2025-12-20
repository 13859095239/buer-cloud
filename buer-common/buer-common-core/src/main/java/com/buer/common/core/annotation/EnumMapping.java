package com.buer.common.core.annotation;

import com.buer.common.core.entity.BaseEnum;

import java.lang.annotation.*;

/**
 * 枚举字段映射注解
 * <p>
 * 用于标记 VO 中的某个字段是枚举的存储值，
 * 工具类会根据该注解找到对应的枚举类，填充描述字段。
 *
 * @author zoulan
 * @since 2025-09-26
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumMapping {

    /**
     * 枚举类类型，必须实现 BaseEnum 接口
     */
    Class<? extends BaseEnum<?>> value();

    /**
     * 描述字段名，默认 = 字段名 + "Desc"
     */
    String descField() default "";
}