package com.buer.common.mybatis.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * FreeMarker 代码生成器
 *
 * @author zoulan
 * @since 2025-07-18
 */
@Service
@RequiredArgsConstructor
public class FreeMarkerTemplate {
    private static final Logger logger = LoggerFactory.getLogger(FreeMarkerTemplate.class);
    private static final String projectPath = System.getProperty("user.dir");

    /**
     * 使用模板引擎生成代码。
     *
     * @param params           生成参数
     * @param templateFilePath 模板文件位置
     * @param filePath         生成文件位置
     */
    @SneakyThrows
    public static void generate(Map<String, Object> params, String templateFilePath, String filePath) {
        // 初始化FreeMarker
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_34);
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configuration.setClassForTemplateLoading(FreeMarkerTemplate.class, "/");
        Template template = configuration.getTemplate(templateFilePath);
        String absolutePath = projectPath + "/" + filePath;
        if (FileUtil.exist(absolutePath)) {
            logger.warn("文件已存在,跳过该文件生成,文件路径:{}", absolutePath);
            return;
        }
        // 开始生成文件
        try (FileOutputStream fileOutputStream = new FileOutputStream(absolutePath)) {
            StringWriter stringWriter = new StringWriter();
            template.process(params, stringWriter);
            IoUtil.write(fileOutputStream, true, stringWriter.toString()
                    .getBytes());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
