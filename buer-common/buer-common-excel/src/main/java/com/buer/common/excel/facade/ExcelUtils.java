package com.buer.common.excel.facade;

import com.buer.common.excel.config.ExcelConfig;
import com.buer.common.excel.core.ExcelExportService;
import com.buer.common.excel.core.ExcelImportService;
import com.buer.common.excel.core.ExcelTemplateService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

/**
 * Excel工具类（统一入口/门面）
 * <p>
 * 基于EasyExcel提供Excel导入导出的统一入口。
 * 按照功能分为三大核心模块：模板下载、数据导出、数据导入。
 * </p>
 * <p>
 * <b>注意</b>：此类是对外提供的统一API入口，使用门面模式封装内部实现细节。
 * </p>
 *
 * <h3>功能模块</h3>
 * <ul>
 *   <li><b>模板下载</b>：downloadTemplate - 下载包含批注说明的Excel模板</li>
 *   <li><b>数据导出</b>：exportExcel - 导出数据到Excel文件</li>
 *   <li><b>数据导入</b>：readExcel - 从Excel文件读取数据</li>
 *   <li><b>配置获取</b>：getConfig - 获取Excel配置信息</li>
 * </ul>
 *
 * <h3>架构说明</h3>
 * <pre>
 * ExcelUtils (门面)
 * ├── core/ExcelTemplateService (模板服务)
 * ├── core/ExcelExportService   (导出服务)
 * ├── core/ExcelImportService   (导入服务)
 * └── core/ExcelValidator       (验证器)
 * </pre>
 *
 * @author zoulan
 * @since 2025-09-29
 */
@Component
@AllArgsConstructor
public class ExcelUtils {

    private final ExcelExportService exportService;
    private final ExcelImportService importService;
    private final ExcelTemplateService templateService;
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
        exportService.writeExcel(response, fileName, data, clazz, null);
    }

    /**
     * 导出Excel到响应流（支持自定义处理）
     *
     * @param response HTTP响应对象
     * @param fileName 文件名（不包含扩展名）
     * @param data     数据列表
     * @param clazz    数据类
     * @param consumer 自定义处理函数（可选）
     * @param <T>      数据类型
     */
    public <T> void exportExcel(HttpServletResponse response, String fileName, List<T> data,
                                Class<T> clazz, Consumer<com.alibaba.excel.ExcelWriter> consumer) {
        exportService.writeExcel(response, fileName, data, clazz, consumer);
    }

    /**
     * 下载Excel模板
     * <p>
     * 导出只包含表头的Excel模板文件，用于数据导入。
     * 模板包含批注说明，帮助用户了解每列数据的填写要求。
     * </p>
     *
     * @param response HTTP响应对象
     * @param fileName 文件名（不包含扩展名）
     * @param clazz    模板数据类
     * @param <T>      数据类型
     */
    public <T> void downloadTemplate(HttpServletResponse response, String fileName, Class<T> clazz) {
        templateService.downloadTemplate(response, fileName, clazz);
    }

    // ==================== 导入功能 ====================

    /**
     * 从输入流读取Excel数据
     * <p>
     * 从输入流中读取Excel文件数据并转换为指定类型的数据列表。
     * </p>
     *
     * @param inputStream 输入流
     * @param clazz       数据类
     * @param <T>         数据类型
     * @return 数据列表
     */
    public <T> List<T> readExcel(InputStream inputStream, Class<T> clazz) {
        return importService.importFromStream(inputStream, clazz);
    }

    /**
     * 从MultipartFile读取Excel数据
     * <p>
     * 从上传的文件中读取Excel数据。
     * </p>
     *
     * @param file  上传的文件
     * @param clazz 数据类
     * @param <T>   数据类型
     * @return 数据列表
     */
    public <T> List<T> readExcel(org.springframework.web.multipart.MultipartFile file, Class<T> clazz) {
        return importService.importFromFile(file, clazz);
    }


    // ==================== 配置获取 ====================

    /**
     * 获取配置信息
     *
     * @return Excel配置
     */
    public ExcelConfig getConfig() {
        return excelConfig;
    }
}
