package com.buer.common.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel 列宽注解
 * <p>
 * 用于在Excel导出时设置列的宽度和是否自动换行。
 * 通过 {@link com.buer.common.excel.handler.ColumnWidthWriteHandler} 处理器自动处理。
 * </p>
 * <p>
 * </p>
 *
 * @author system
 * @see com.buer.common.excel.handler.ColumnWidthWriteHandler
 * @since 2024-01-01
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumnWidth {

    /**
     * 列宽（字符数）
     * <p>
     * Excel中的列宽单位是字符数，大约1个字符 = 256个单位。
     * 如果不设置或设置为0，则使用默认列宽。
     * </p>
     *
     * @return 列宽字符数，默认0表示使用默认宽度
     */
    int width() default 0;

    /**
     * 是否自动换行
     * <p>
     * true: 允许单元格内容自动换行
     * false: 不允许换行（默认值）
     * </p>
     *
     * @return 是否自动换行，默认false
     */
    boolean wrapText() default false;
}
