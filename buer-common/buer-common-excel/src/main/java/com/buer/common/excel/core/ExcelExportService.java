package com.buer.common.excel.core;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.buer.common.excel.config.ExcelConfig;
import com.buer.common.excel.handler.CommentWriteHandler;
import com.buer.common.excel.support.ExcelResponseUtils;
import com.buer.common.excel.support.ExcelStyleUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Excel导出服务
 * <p>
 * 负责Excel数据导出的核心功能。
 * 支持数据导出和自定义处理。
 * </p>
 *
 * @author zoulan
 * @since 2026-01-18
 */
@Service
@AllArgsConstructor
public class ExcelExportService {

    private static final Logger log = LoggerFactory.getLogger(ExcelExportService.class);

    private final ExcelConfig excelConfig;

    /**
     * 导出Excel到输出流
     *
     * @param outputStream 输出流
     * @param data         数据列表
     * @param clazz        数据类
     * @param consumer     自定义处理函数（可选）
     * @param <T>          数据类型
     */
    public <T> void export(OutputStream outputStream, List<T> data, Class<T> clazz, Consumer<ExcelWriter> consumer) {
        try {
            // 处理空数据
            List<T> exportData = CollUtil.isEmpty(data) ? new ArrayList<>() : data;
            if (CollUtil.isEmpty(data)) {
                log.warn("导出数据为空");
            }

            // 写入Excel数据
            writeExcel(outputStream, exportData, clazz, consumer);

            log.info("Excel导出成功，数据行数：{}", exportData.size());

        } catch (IOException e) {
            log.error("Excel导出失败，错误信息：{}", e.getMessage(), e);
            throw new RuntimeException("Excel导出失败：" + e.getMessage(), e);
        }
    }

    /**
     * 导出Excel到HTTP响应
     *
     * @param response HTTP响应对象
     * @param fileName 文件名（不包含扩展名）
     * @param data     数据列表
     * @param clazz    数据类
     * @param consumer 自定义处理函数（可选）
     * @param <T>      数据类型
     */
    public <T> void exportToResponse(HttpServletResponse response, String fileName, List<T> data,
                                     Class<T> clazz, Consumer<ExcelWriter> consumer) {
        try {
            // 设置响应头
            ExcelResponseUtils.setResponseHeaders(response, fileName);
            // 导出数据
            export(response.getOutputStream(), data, clazz, consumer);

        } catch (IOException e) {
            log.error("Excel导出失败，文件名：{}，错误信息：{}", fileName, e.getMessage(), e);
            throw new RuntimeException("Excel导出失败：" + e.getMessage(), e);
        }
    }

    /**
     * 写入Excel数据到输出流
     *
     * @param outputStream 输出流
     * @param data         数据列表
     * @param clazz        数据类
     * @param consumer     自定义处理函数（可选）
     * @param <T>          数据类型
     * @throws IOException IO异常
     */
    private <T> void writeExcel(OutputStream outputStream, List<T> data, Class<T> clazz,
                                Consumer<ExcelWriter> consumer) throws IOException {
        // 创建Excel写入器，注册样式策略和批注处理器
        ExcelWriter excelWriter = EasyExcel.write(outputStream, clazz)
            .registerWriteHandler(ExcelStyleUtils.createCellStyleStrategy())
            .registerWriteHandler(new CommentWriteHandler(clazz))
            .build();

        // 执行自定义处理（如果提供）
        if (consumer != null) {
            consumer.accept(excelWriter);
        }

        // 创建工作表
        WriteSheet writeSheet = EasyExcel.writerSheet(excelConfig.getDefaultSheetName())
            .build();

        // 写入数据
        excelWriter.write(data, writeSheet);

        // 关闭写入器
        excelWriter.finish();
    }
}
