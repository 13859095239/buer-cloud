package com.buer.common.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel 批注注解
 * <p>
 * 用于在Excel导出时为单元格添加批注说明。
 * 通过 {@link com.buer.common.excel.handler.CommentWriteHandler} 处理器自动处理。
 * </p>
 *
 * @author zoulan
 * @see com.buer.common.excel.handler.CommentWriteHandler
 * @since 2026-01-18
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelComment {

    /**
     * 批注内容
     * <p>
     * 支持多行文本，使用 {@code \n} 分隔。
     * 如果为空字符串，则不会添加批注。
     * </p>
     *
     * @return 批注文本内容
     */
    String value() default "";
}
