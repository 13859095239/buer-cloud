package com.buer.common.excel.support;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.*;

/**
 * Excel 样式工具类
 * <p>
 * 提供Excel表头和内容的样式配置。
 * 包含字体、颜色、对齐方式、边框等样式设置。
 * </p>
 *
 * @author zoulan
 * @since 2025-09-29
 */
public class ExcelStyleUtils {

    /**
     * 表头字体大小
     */
    private static final short HEAD_FONT_SIZE = 12;

    /**
     * 内容字体大小
     */
    private static final short CONTENT_FONT_SIZE = 11;

    /**
     * 表头行高（磅）
     */
    private static final float HEAD_ROW_HEIGHT = 20.0f;

    /**
     * 内容行高（磅）
     */
    private static final float CONTENT_ROW_HEIGHT = 18.0f;

    /**
     * 字体名称
     */
    private static final String FONT_NAME = "宋体";

    /**
     * 获取表头样式
     * <p>
     * 表头样式：白色背景、12号宋体加粗、居中对齐、黑色边框
     * </p>
     *
     * @return 表头样式
     */
    public static WriteCellStyle getHeadWriteCellStyle() {
        WriteCellStyle style = new WriteCellStyle();

        // 设置背景色
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());

        // 设置字体
        WriteFont font = new WriteFont();
        font.setFontHeightInPoints(HEAD_FONT_SIZE);
        font.setBold(true);
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setFontName(FONT_NAME);
        style.setWriteFont(font);

        // 设置对齐方式
        style.setHorizontalAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // 设置边框
        setBorders(style, IndexedColors.BLACK);

        return style;
    }

    /**
     * 获取内容样式
     * <p>
     * 内容样式：11号宋体、左对齐、灰色边框
     * </p>
     *
     * @return 内容样式
     */
    public static WriteCellStyle getContentWriteCellStyle() {
        WriteCellStyle style = new WriteCellStyle();

        // 设置字体
        WriteFont font = new WriteFont();
        font.setFontHeightInPoints(CONTENT_FONT_SIZE);
        font.setFontName(FONT_NAME);
        style.setWriteFont(font);

        // 设置对齐方式
        style.setHorizontalAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // 设置边框
        setBorders(style, IndexedColors.GREY_25_PERCENT);

        return style;
    }

    /**
     * 设置单元格边框样式
     *
     * @param style  单元格样式
     * @param color  边框颜色
     */
    private static void setBorders(WriteCellStyle style, IndexedColors color) {
        short colorIndex = color.getIndex();
        BorderStyle borderStyle = BorderStyle.THIN;

        style.setBorderTop(borderStyle);
        style.setBorderBottom(borderStyle);
        style.setBorderLeft(borderStyle);
        style.setBorderRight(borderStyle);
        style.setTopBorderColor(colorIndex);
        style.setBottomBorderColor(colorIndex);
        style.setLeftBorderColor(colorIndex);
        style.setRightBorderColor(colorIndex);
    }

    /**
     * 创建单元格样式策略（包含表头和内容样式）
     * <p>
     * 使用HorizontalCellStyleStrategy确保样式正确应用。
     * 同时设置表头行高为20，内容行高为18。
     * </p>
     *
     * @return 样式策略
     */
    public static HorizontalCellStyleStrategy createCellStyleStrategy() {
        return new HorizontalCellStyleStrategy(getHeadWriteCellStyle(), getContentWriteCellStyle()) {
            @Override
            protected void setHeadCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
                super.setHeadCellStyle(cell, head, relativeRowIndex);
                setRowHeight(cell, HEAD_ROW_HEIGHT);
            }

            @Override
            protected void setContentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
                super.setContentCellStyle(cell, head, relativeRowIndex);
                setRowHeight(cell, CONTENT_ROW_HEIGHT);
            }

            /**
             * 设置行高
             *
             * @param cell   单元格
             * @param height 行高（磅）
             */
            private void setRowHeight(Cell cell, float height) {
                if (cell != null && cell.getRow() != null) {
                    cell.getRow().setHeightInPoints(height);
                }
            }
        };
    }
}