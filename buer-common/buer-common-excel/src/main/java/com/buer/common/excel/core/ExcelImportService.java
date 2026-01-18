package com.buer.common.excel.core;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import cn.hutool.core.io.IoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel导入服务
 * <p>
 * 负责Excel数据导入的核心功能。
 * </p>
 *
 * @author zoulan
 * @since 2026-01-18
 */
@Service
public class ExcelImportService {

    private static final Logger log = LoggerFactory.getLogger(ExcelImportService.class);

    /**
     * 从输入流读取Excel数据
     * <p>
     * 注意：此方法不会关闭输入流，调用方需要负责流的关闭。
     * </p>
     *
     * @param inputStream 输入流
     * @param clazz       数据类
     * @param <T>         数据类型
     * @return 数据列表
     * @throws RuntimeException 如果读取Excel失败
     */
    public <T> List<T> importFromStream(InputStream inputStream, Class<T> clazz) {
        if (inputStream == null) {
            throw new IllegalArgumentException("输入流不能为空");
        }
        if (clazz == null) {
            throw new IllegalArgumentException("数据类不能为空");
        }

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
            log.error("Excel读取失败，数据类：{}，错误信息：{}", clazz.getName(), e.getMessage(), e);
            throw new RuntimeException("Excel读取失败：" + e.getMessage(), e);
        }

        return dataList;
    }

    /**
     * 从MultipartFile读取Excel数据
     * <p>
     * 此方法会自动管理输入流的生命周期，无需手动关闭。
     * </p>
     *
     * @param file  上传的文件
     * @param clazz 数据类
     * @param <T>   数据类型
     * @return 数据列表
     * @throws RuntimeException 如果读取Excel失败
     */
    public <T> List<T> importFromFile(MultipartFile file, Class<T> clazz) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }
        if (clazz == null) {
            throw new IllegalArgumentException("数据类不能为空");
        }

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            List<T> dataList = importFromStream(inputStream, clazz);
            log.info("Excel读取成功，文件名：{}，数据行数：{}", file.getOriginalFilename(), dataList.size());
            return dataList;

        } catch (IOException e) {
            log.error("读取上传文件失败，文件名：{}，错误信息：{}", file.getOriginalFilename(), e.getMessage(), e);
            throw new RuntimeException("读取上传文件失败：" + e.getMessage(), e);
        } finally {
            // 确保流被关闭
            IoUtil.close(inputStream);
        }
    }
}
