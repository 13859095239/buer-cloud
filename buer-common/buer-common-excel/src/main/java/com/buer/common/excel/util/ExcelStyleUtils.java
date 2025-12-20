package com.buer.common.excel.util;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.*;

/**
 * Excel样式工具类
 * 提供Excel表头和内容的样式配置
 *
 * @author zoulan
 * @since 2025-09-29
 */
public class ExcelStyleUtils {

    /**
     * 获取表头样式
     *
     * @return 表头样式
     */
    public static WriteCellStyle getHeadWriteCellStyle() {
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();

        // 设置表头背景色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());

        // 设置表头字体
        WriteFont headWriteFont = new WriteFont();
        // 字体大小
        headWriteFont.setFontHeightInPoints((short) 12);
        // 加粗
        headWriteFont.setBold(true);
        // 黑色字体
        headWriteFont.setColor(IndexedColors.BLACK.getIndex());
        // 字体名称
        headWriteFont.setFontName("宋体");
        headWriteCellStyle.setWriteFont(headWriteFont);

        // 设置对齐方式
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 设置边框
        headWriteCellStyle.setBorderTop(BorderStyle.THIN);
        headWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        headWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        headWriteCellStyle.setBorderRight(BorderStyle.THIN);
        headWriteCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headWriteCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headWriteCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headWriteCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

        return headWriteCellStyle;
    }

    /**
     * 获取内容样式
     *
     * @return 内容样式
     */
    public static WriteCellStyle getContentWriteCellStyle() {
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();

        // 设置内容字体
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 11);
        // 字体名称
        contentWriteFont.setFontName("宋体");
        contentWriteCellStyle.setWriteFont(contentWriteFont);

        // 设置对齐方式
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        // 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 设置边框
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        contentWriteCellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        contentWriteCellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        contentWriteCellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        return contentWriteCellStyle;
    }

    /**
     * 创建单元格样式策略（包含表头和内容样式）
     * 使用HorizontalCellStyleStrategy确保样式正确应用
     *
     * @return 样式策略
     */
    public static HorizontalCellStyleStrategy createCellStyleStrategy() {
        // 创建样式策略
        return new HorizontalCellStyleStrategy(getHeadWriteCellStyle(), getContentWriteCellStyle()) {
            @Override
            protected void setHeadCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
                super.setHeadCellStyle(cell, head, relativeRowIndex);
                // 设置表头行高
                if (cell != null && cell.getRow() != null) {
                    cell.getRow()
                        .setHeightInPoints(20);
                }
            }

            @Override
            protected void setContentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
                super.setContentCellStyle(cell, head, relativeRowIndex);
                // 设置内容行高
                if (cell != null && cell.getRow() != null) {
                    cell.getRow()
                        .setHeightInPoints(18);
                }
            }
        };
    }
}