package com.buer.common.excel.constant;

/**
 * Excel 常量类
 * 定义Excel相关的常量，包括文件类型、扩展名等。
 *
 * @author zoulan
 * @since 2026-01-18
 */
public final class ExcelConstants {

    /**
     * Excel 文件MIME类型
     */
    public static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    /**
     * Excel 文件扩展名
     */
    public static final String FILE_EXTENSION = ".xlsx";

    /**
     * 模板文件名后缀
     */
    public static final String TEMPLATE_SUFFIX = "_模板";

    /**
     * MB 转字节的转换因子
     */
    public static final int BYTES_PER_MB = 1024 * 1024;

}
