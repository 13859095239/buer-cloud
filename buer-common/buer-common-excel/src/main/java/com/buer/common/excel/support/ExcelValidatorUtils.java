package com.buer.common.excel.support;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.buer.common.excel.config.ExcelConfig;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.buer.common.excel.constant.ExcelConstants.BYTES_PER_MB;

/**
 * Excel 验证器
 * <p>
 * 负责Excel文件的格式、大小、行数等验证逻辑。
 * </p>
 *
 * @author zoulan
 * @since 2026-01-18
 */
@Component
@AllArgsConstructor
public class ExcelValidatorUtils {

    private static final Logger log = LoggerFactory.getLogger(ExcelValidatorUtils.class);

    private final ExcelConfig excelConfig;

    /**
     * 验证Excel文件格式
     *
     * @param fileName 文件名
     * @return 是否为有效的Excel文件
     */
    public boolean isValidExcelFile(String fileName) {
        if (StrUtil.isBlank(fileName)) {
            return false;
        }

        String lowerFileName = fileName.toLowerCase();
        for (String format : excelConfig.getSupportedFormats()) {
            if (lowerFileName.endsWith(format.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证文件大小
     *
     * @param file 上传的文件
     * @return 是否在允许的大小范围内
     */
    public boolean isValidFileSize(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        long maxSize = excelConfig.getMaxFileSize() * BYTES_PER_MB;
        return file.getSize() <= maxSize;
    }

    /**
     * 验证导入数据行数
     *
     * @param dataList 数据列表
     * @return 是否在允许的行数范围内
     */
    public boolean isValidImportRows(List<?> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return true;
        }

        return dataList.size() <= excelConfig.getMaxImportRows();
    }

    /**
     * 验证上传文件
     * <p>
     * 综合验证文件是否为空、格式是否正确、大小是否超限。
     * </p>
     *
     * @param file 上传的文件
     * @throws IllegalArgumentException 如果文件验证失败
     */
    public void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        String fileName = file.getOriginalFilename();
        if (!isValidExcelFile(fileName)) {
            throw new IllegalArgumentException(
                String.format("文件格式不正确，仅支持%s格式", String.join("、", excelConfig.getSupportedFormats())));
        }

        if (!isValidFileSize(file)) {
            throw new IllegalArgumentException(
                String.format("文件大小超过限制，最大允许%dMB", excelConfig.getMaxFileSize()));
        }
    }
}
