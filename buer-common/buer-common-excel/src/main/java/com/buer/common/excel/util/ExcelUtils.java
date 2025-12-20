package com.buer.common.excel.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.buer.common.excel.config.ExcelConfig;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Excel工具类
 * 基于EasyExcel 4.x版本，集成Hutool工具类
 * 提供Excel导入导出的通用方法
 *
 * @author zoulan
 * @since 2025-09-29
 */
@Slf4j
@Component
@AllArgsConstructor
public class ExcelUtils {

    private final ExcelConfig excelConfig;

    /**
     * 导出Excel到响应流
     *
     * @param response HTTP响应对象
     * @param fileName 文件名（不包含扩展名）
     * @param data     数据列表
     * @param clazz    数据类
     * @param <T>      数据类型
     */
    public <T> void exportExcel(HttpServletResponse response, String fileName, List<T> data, Class<T> clazz) {
        try {
            // 设置响应头
            setResponseHeaders(response, fileName);

            // 创建Excel写入器
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), clazz)
                .build();

            // 创建工作表
            WriteSheet writeSheet = EasyExcel.writerSheet(excelConfig.getDefaultSheetName())
                .build();

            // 写入数据
            excelWriter.write(data, writeSheet);

            // 关闭写入器
            excelWriter.finish();

            log.info("Excel导出成功，文件名：{}，数据行数：{}", fileName, data.size());

        } catch (IOException e) {
            log.error("Excel导出失败，文件名：{}，错误信息：{}", fileName, e.getMessage(), e);
            throw new RuntimeException("Excel导出失败：" + e.getMessage(), e);
        }
    }

    /**
     * 导出Excel到响应流（带自定义处理）
     *
     * @param response HTTP响应对象
     * @param fileName 文件名（不包含扩展名）
     * @param data     数据列表
     * @param clazz    数据类
     * @param consumer 自定义处理函数
     * @param <T>      数据类型
     */
    public <T> void exportExcel(HttpServletResponse response, String fileName, List<T> data,
                                Class<T> clazz, Consumer<ExcelWriter> consumer) {
        try {
            // 设置响应头
            setResponseHeaders(response, fileName);

            // 创建Excel写入器
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), clazz)
                .build();

            // 执行自定义处理
            if (consumer != null) {
                consumer.accept(excelWriter);
            }

            // 创建工作表并写入数据
            WriteSheet writeSheet = EasyExcel.writerSheet(excelConfig.getDefaultSheetName())
                .build();
            excelWriter.write(data, writeSheet);

            // 关闭写入器
            excelWriter.finish();

            log.info("Excel导出成功，文件名：{}，数据行数：{}", fileName, data.size());

        } catch (IOException e) {
            log.error("Excel导出失败，文件名：{}，错误信息：{}", fileName, e.getMessage(), e);
            throw new RuntimeException("Excel导出失败：" + e.getMessage(), e);
        }
    }

    /**
     * 从输入流读取Excel数据
     *
     * @param inputStream 输入流
     * @param clazz       数据类
     * @param <T>         数据类型
     * @return 数据列表
     */
    public <T> List<T> readExcel(InputStream inputStream, Class<T> clazz) {
        List<T> dataList = new ArrayList<>();

        try {
            EasyExcel.read(inputStream, clazz, new AnalysisEventListener<T>() {
                    @Override
                    public void invoke(T data, AnalysisContext context) {
                        dataList.add(data);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                        log.info("Excel读取完成，共读取{}行数据", dataList.size());
                    }
                })
                .sheet()
                .doRead();

        } catch (Exception e) {
            log.error("Excel读取失败，错误信息：{}", e.getMessage(), e);
            throw new RuntimeException("Excel读取失败：" + e.getMessage(), e);
        } finally {
            IoUtil.close(inputStream);
        }

        return dataList;
    }

    /**
     * 从MultipartFile读取Excel数据
     *
     * @param file  上传的文件
     * @param clazz 数据类
     * @param <T>   数据类型
     * @return 数据列表
     */
    public <T> List<T> readExcel(MultipartFile file, Class<T> clazz) {
        // 验证文件
        validateFile(file);

        try {
            return readExcel(file.getInputStream(), clazz);
        } catch (IOException e) {
            log.error("读取上传文件失败，文件名：{}，错误信息：{}", file.getOriginalFilename(), e.getMessage(), e);
            throw new RuntimeException("读取上传文件失败：" + e.getMessage(), e);
        }
    }

    /**
     * 下载Excel模板
     *
     * @param response HTTP响应对象
     * @param fileName 文件名（不包含扩展名）
     * @param clazz    模板数据类
     * @param <T>      数据类型
     */
    public <T> void downloadTemplate(HttpServletResponse response, String fileName, Class<T> clazz) {
        try {
            // 设置响应头
            setResponseHeaders(response, fileName + "_模板");

            // 创建Excel写入器
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), clazz)
                .build();

            // 创建工作表
            WriteSheet writeSheet = EasyExcel.writerSheet(excelConfig.getDefaultSheetName())
                .build();

            // 写入空数据（只写入表头）
            excelWriter.write(new ArrayList<>(), writeSheet);

            // 关闭写入器
            excelWriter.finish();

            log.info("Excel模板下载成功，文件名：{}", fileName);

        } catch (IOException e) {
            log.error("Excel模板下载失败，文件名：{}，错误信息：{}", fileName, e.getMessage(), e);
            throw new RuntimeException("Excel模板下载失败：" + e.getMessage(), e);
        }
    }

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

        long maxSize = excelConfig.getMaxFileSize() * 1024 * 1024L; // 转换为字节
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
     *
     * @param file 上传的文件
     */
    public void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        if (!isValidExcelFile(file.getOriginalFilename())) {
            throw new IllegalArgumentException("文件格式不正确，仅支持" +
                String.join("、", excelConfig.getSupportedFormats()) + "格式");
        }

        if (!isValidFileSize(file)) {
            throw new IllegalArgumentException("文件大小超过限制，最大允许" +
                excelConfig.getMaxFileSize() + "MB");
        }
    }

    /**
     * 设置HTTP响应头
     *
     * @param response HTTP响应对象
     * @param fileName 文件名
     */
    private void setResponseHeaders(HttpServletResponse response, String fileName) {
        try {
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            
            // 设置缓存控制
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            
            // 设置CORS头
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition, Content-Type");

            // 设置文件名，支持中文
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name())
                .replaceAll("\\+", "%20");
            
            // 同时设置两种格式的Content-Disposition头，确保兼容性
            response.setHeader("Content-Disposition", 
                "attachment; filename=\"" + encodedFileName + ".xlsx\"; filename*=UTF-8''" + encodedFileName + ".xlsx");

        } catch (Exception e) {
            log.error("设置响应头失败，文件名：{}，错误信息：{}", fileName, e.getMessage(), e);
            throw new RuntimeException("设置响应头失败：" + e.getMessage(), e);
        }
    }
}
