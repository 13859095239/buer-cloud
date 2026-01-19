package com.buer.common.excel.core;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.buer.common.excel.config.ExcelConfig;
import com.buer.common.excel.handler.ColumnWidthWriteHandler;
import com.buer.common.excel.handler.CommentWriteHandler;
import com.buer.common.excel.support.ExcelResponseUtils;
import com.buer.common.excel.support.ExcelStyleUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.buer.common.excel.constant.ExcelConstants.TEMPLATE_SUFFIX;

/**
 * Excel模板服务
 * <p>
 * 负责Excel模板下载相关的业务逻辑。
 * 提供包含批注说明的Excel模板，用于数据导入。
 * </p>
 *
 * @author zoulan
 * @since 2026-01-18
 */
@Service
@AllArgsConstructor
public class ExcelTemplateService {

    private static final Logger log = LoggerFactory.getLogger(ExcelTemplateService.class);

    private final ExcelConfig excelConfig;

    /**
     * 下载Excel模板到HTTP响应
     *
     * @param response HTTP响应对象
     * @param fileName 文件名（不包含扩展名）
     * @param clazz    模板数据类
     * @param <T>      数据类型
     */
    public <T> void downloadTemplate(HttpServletResponse response, String fileName, Class<T> clazz) {
        try {
            // 设置响应头
            ExcelResponseUtils.setResponseHeaders(response, fileName + TEMPLATE_SUFFIX);

            // 创建Excel写入器，注册样式策略、批注处理器和列宽处理器
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), clazz)
                .registerWriteHandler(ExcelStyleUtils.createCellStyleStrategy())
                .registerWriteHandler(new CommentWriteHandler(clazz))
                .registerWriteHandler(new ColumnWidthWriteHandler(clazz))
                .build();

            // 创建工作表
            WriteSheet writeSheet = EasyExcel.writerSheet(excelConfig.getDefaultSheetName())
                .build();

            // 写入空数据（只写入表头）
            excelWriter.write(java.util.Collections.emptyList(), writeSheet);

            // 关闭写入器
            excelWriter.finish();

            log.info("Excel模板下载成功，文件名：{}", fileName);

        } catch (IOException e) {
            log.error("Excel模板下载失败，文件名：{}，错误信息：{}", fileName, e.getMessage(), e);
            throw new RuntimeException("Excel模板下载失败：" + e.getMessage(), e);
        }
    }
}
