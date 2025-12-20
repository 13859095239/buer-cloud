package com.buer.common.log.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author zoulan
 * @since 2023-06-17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 描述
     *
     * @return {String}
     */
    String value() default "";


    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;


    /**
     * 排除指定的请求参数
     */
    String[] excludeParamNames() default {};
}
