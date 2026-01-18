package com.buer.common.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel批注注解
 * <p>
 * 用于在Excel导出时为单元格添加批注说明。
 * 该注解需要配合 {@link com.alibaba.excel.annotation.ExcelProperty} 使用，
 * 通过 {@link com.buer.common.excel.handler.CommentWriteHandler} 处理器自动处理。
 * </p>
 * <p>
 * 使用示例：
 * <pre>
 * {@code
 * @ExcelProperty(value = "用户名", index = 0)
 * @ExcelComment(value = "用户名不能为空，长度必须在2-20个字符之间")
 * private String username;
 * }
 * </pre>
 * </p>
 *
 * @author zoulan
 * @since 2026-01-18
 * @see com.buer.common.excel.handler.CommentWriteHandler
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
