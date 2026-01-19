package com.buer.common.excel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Excel配置类
 * 用于配置Excel导入导出的相关参数
 *
 * @author zoulan
 * @since 2025-09-29
 */
@Data
@Component
@ConfigurationProperties(prefix = "excel")
public class ExcelConfig {

    /**
     * 最大文件大小（MB）
     */
    private int maxFileSize = 10;

    /**
     * 最大导入行数
     */
    private int maxImportRows = 1000;

    /**
     * 默认工作表名称
     */
    private String defaultSheetName = "数据";

    /**
     * 支持的 Excel文件格式
     */
    private String[] supportedFormats = {".xlsx", ".xls"};

    /**
     * 是否启用自动列宽
     */
    private boolean autoColumnWidth = true;

    /**
     * 默认列宽
     */
    private int defaultColumnWidth = 20;

    /**
     * 默认行高
     */
    private int defaultRowHeight = 20;
}
