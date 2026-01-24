package com.buer.common.excel.support;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Excel响应工具类
 * <p>
 * 提供HTTP响应头设置的公共方法，用于Excel文件下载。
 * </p>
 *
 * @author zoulan
 * @since 2026-01-18
 */

import static com.buer.common.excel.constant.ExcelConstants.CONTENT_TYPE;
import static com.buer.common.excel.constant.ExcelConstants.FILE_EXTENSION;

public final class ExcelResponseUtils {

    private static final Logger log = LoggerFactory.getLogger(ExcelResponseUtils.class);

    private ExcelResponseUtils() {
        // 工具类不允许实例化
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * 设置Excel文件下载的HTTP响应头
     * <p>
     * 包括：
     * - Content-Type: Excel文件MIME类型
     * - Content-Disposition: 附件下载，支持中文文件名
     * - Cache-Control: 禁用缓存
     * - CORS: 跨域支持
     * </p>
     *
     * @param response HTTP响应对象
     * @param fileName 文件名（不包含扩展名）
     * @throws RuntimeException 如果设置响应头失败
     */
    public static void setResponseHeaders(HttpServletResponse response, String fileName) {
        try {
            // 设置响应头
            response.setContentType(CONTENT_TYPE);
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
                StrUtil.format("attachment; filename=\"{}{}\"; filename*=UTF-8''{}{}",
                    encodedFileName, FILE_EXTENSION, encodedFileName, FILE_EXTENSION));

        } catch (Exception e) {
            log.error("设置响应头失败，文件名：{}，错误信息：{}", fileName, e.getMessage(), e);
            throw new RuntimeException("设置响应头失败：" + e.getMessage(), e);
        }
    }
}
