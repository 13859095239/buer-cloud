package com.buer.common.excel.util;

import cn.hutool.core.collection.CollUtil;
import com.buer.common.excel.config.ExcelConfig;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 简化版Excel工具类
 * 提供更简单的Excel操作接口，减少使用复杂度
 *
 * @author zoulan
 * @since 2025-09-29
 */
@Slf4j
@Component
@AllArgsConstructor
public class SimpleExcelUtils {
    private final ExcelUtils excelUtils;
    private final ExcelConfig excelConfig;

    /**
     * 简单导出Excel
     *
     * @param response HTTP响应对象
     * @param fileName 文件名（不包含扩展名）
     * @param data     数据列表
     * @param clazz    数据类
     * @param <T>      数据类型
     */
    public <T> void exportExcel(HttpServletResponse response, String fileName, List<T> data, Class<T> clazz) {
        if (CollUtil.isEmpty(data)) {
            log.warn("导出数据为空，文件名：{}", fileName);
            data = CollUtil.newArrayList();
        }

        excelUtils.exportExcel(response, fileName, data, clazz);
    }

    /**
     * 简单读取Excel
     *
     * @param file  上传的文件
     * @param clazz 数据类
     * @param <T>   数据类型
     * @return 数据列表
     */
    public <T> List<T> readExcel(MultipartFile file, Class<T> clazz) {
        // 验证文件
        excelUtils.validateFile(file);

        // 读取数据
        List<T> dataList = excelUtils.readExcel(file, clazz);

        // 验证数据行数
        if (!excelUtils.isValidImportRows(dataList)) {
            throw new IllegalArgumentException("导入数据行数超过限制，最大允许" +
                excelConfig.getMaxImportRows() + "行");
        }

        log.info("Excel读取成功，文件名：{}，数据行数：{}", file.getOriginalFilename(), dataList.size());
        return dataList;
    }

    /**
     * 简单下载模板
     *
     * @param response HTTP响应对象
     * @param fileName 文件名（不包含扩展名）
     * @param clazz    模板数据类
     * @param <T>      数据类型
     */
    public <T> void downloadTemplate(HttpServletResponse response, String fileName, Class<T> clazz) {
        excelUtils.downloadTemplate(response, fileName, clazz);
    }

    /**
     * 验证Excel文件
     *
     * @param fileName 文件名
     * @return 是否为有效的Excel文件
     */
    public boolean isValidExcelFile(String fileName) {
        return excelUtils.isValidExcelFile(fileName);
    }

    /**
     * 验证文件大小
     *
     * @param file 上传的文件
     * @return 是否在允许的大小范围内
     */
    public boolean isValidFileSize(MultipartFile file) {
        return excelUtils.isValidFileSize(file);
    }

    /**
     * 获取配置信息
     *
     * @return Excel配置
     */
    public ExcelConfig getConfig() {
        return excelConfig;
    }
}
