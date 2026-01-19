package com.buer.common.excel.handler;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.buer.common.excel.annotation.ExcelColumnWidth;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Excel 列宽处理器
 * <p>
 * 用于在Excel导出时自动设置列的宽度和是否自动换行。
 * 实现 {@link SheetWriteHandler} 用于设置列宽，
 * 实现 {@link RowWriteHandler} 用于设置单元格的换行样式。
 * </p>
 *
 * @author system
 * @since 2024-01-01
 */
public class ColumnWidthWriteHandler implements SheetWriteHandler, RowWriteHandler {

    private final Class<?> clazz;

    /**
     * 列宽映射表：列索引 -> 列宽（字符数）
     */
    private final Map<Integer, Integer> columnWidthMap = new HashMap<>();

    /**
     * 换行设置映射表：列索引 -> 是否换行
     */
    private final Map<Integer, Boolean> wrapTextMap = new HashMap<>();

    public ColumnWidthWriteHandler(Class<?> clazz) {
        this.clazz = clazz;
        initColumnWidthMap();
    }

    /**
     * 初始化列宽映射
     * <p>
     * 扫描类中的字段，查找带有 {@link ExcelColumnWidth} 注解的字段，
     * 并根据 {@link ExcelProperty} 的 index 建立映射关系。
     * </p>
     */
    private void initColumnWidthMap() {
        for (Field field : clazz.getDeclaredFields()) {
            ExcelColumnWidth columnWidth = field.getAnnotation(ExcelColumnWidth.class);
            if (columnWidth == null) {
                continue;
            }

            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (excelProperty == null) {
                continue;
            }

            int index = excelProperty.index();
            if (index >= 0) {
                // 如果设置了宽度，则记录到映射表中
                if (columnWidth.width() > 0) {
                    columnWidthMap.put(index, columnWidth.width());
                }
                // 记录是否换行设置
                wrapTextMap.put(index, columnWidth.wrapText());
            }
        }
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder,
                                 WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();

        // 设置列宽
        columnWidthMap.forEach((columnIndex, width) -> {
            // Excel列宽单位转换：1个字符 ≈ 256个单位
            sheet.setColumnWidth(columnIndex, width * 256);
        });
    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder,
                                 WriteTableHolder writeTableHolder,
                                 Row row,
                                 Integer relativeRowIndex,
                                 Boolean isHead) {
        if (row == null || wrapTextMap.isEmpty()) {
            return;
        }

        // wrapText 只应用到内容行，表头通常不需要换行
        // 如果需要对表头和内容分别设置，可以通过 isHead 参数区分
        if (Boolean.TRUE.equals(isHead)) {
            // 表头行：默认不换行
            return;
        }

        // 为内容行的单元格设置换行样式
        wrapTextMap.forEach((columnIndex, wrapText) -> {
            Cell cell = row.getCell(columnIndex);
            if (cell != null) {
                setWrapTextForCell(cell, wrapText);
            }
        });
    }

    /**
     * 为单元格设置换行样式
     *
     * @param cell     单元格
     * @param wrapText 是否换行
     */
    private void setWrapTextForCell(Cell cell, boolean wrapText) {
        org.apache.poi.ss.usermodel.CellStyle style = cell.getCellStyle();
        if (style == null) {
            // 如果没有样式，创建一个新的
            Sheet sheet = cell.getSheet();
            Workbook workbook = sheet.getWorkbook();
            style = workbook.createCellStyle();
            cell.setCellStyle(style);
        } else {
            // 如果已有样式，需要克隆避免影响其他单元格
            Sheet sheet = cell.getSheet();
            Workbook workbook = sheet.getWorkbook();
            org.apache.poi.ss.usermodel.CellStyle newStyle = workbook.createCellStyle();
            newStyle.cloneStyleFrom(style);
            style = newStyle;
            cell.setCellStyle(style);
        }
        style.setWrapText(wrapText);
    }
}
