package com.buer.common.excel.handler;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.buer.common.excel.annotation.ExcelComment;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Excel 批注处理器
 * <p>
 * 用于在Excel导出时自动为表头单元格添加批注说明。
 * </p>
 *
 * @author zoulan
 * @since 2026-01-18
 */
public class CommentWriteHandler implements RowWriteHandler {

    private final Class<?> clazz;

    /**
     * 批注文本映射表：列索引 -> 批注文本
     */
    private final Map<Integer, String> commentTextMap = new HashMap<>();

    /**
     * 批注行索引映射表：列索引 -> 最后一行表头索引
     */
    private final Map<Integer, Integer> commentRowIndexMap = new HashMap<>();

    public CommentWriteHandler(Class<?> clazz) {
        this.clazz = clazz;
        initCommentMap();
    }

    /**
     * 初始化批注映射
     * <p>
     * 根据 {@link ExcelProperty} 的 value 数组长度计算最后一行表头的索引。
     * 例如：value = {"用户信息", "用户名"}，数组长度为2，最后一行索引为 2-1=1
     * </p>
     */
    private void initCommentMap() {
        for (Field field : clazz.getDeclaredFields()) {
            ExcelComment excelComment = field.getAnnotation(ExcelComment.class);
            if (excelComment == null || excelComment.value().isEmpty()) {
                continue;
            }

            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (excelProperty == null) {
                continue;
            }

            int index = excelProperty.index();
            if (index >= 0) {
                // 根据 value 数组长度计算最后一行表头的索引
                String[] values = excelProperty.value();
                int lastHeadRowIndex = values.length > 0 ? values.length - 1 : 0;

                commentTextMap.put(index, excelComment.value());
                commentRowIndexMap.put(index, lastHeadRowIndex);
            }
        }
    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder,
                                WriteTableHolder writeTableHolder,
                                Row row,
                                Integer relativeRowIndex,
                                Boolean isHead) {
        // 只在表头行处理
        if (!Boolean.TRUE.equals(isHead) || relativeRowIndex == null) {
            return;
        }

        // 遍历批注映射，找到当前行需要添加批注的列
        commentTextMap.forEach((columnIndex, commentText) -> {
            // 如果当前行的相对行索引等于该列对应的最后一行表头索引，则添加批注
            Integer lastHeadRowIndex = commentRowIndexMap.get(columnIndex);
            if (lastHeadRowIndex != null && relativeRowIndex.equals(lastHeadRowIndex)) {
                Cell cell = row.getCell(columnIndex);
                if (cell != null) {
                    addComment(writeSheetHolder, cell, commentText);
                }
            }
        });
    }

    /**
     * 为单元格添加批注
     *
     * @param writeSheetHolder 工作表持有者
     * @param cell             单元格
     * @param commentText      批注文本
     */
    private void addComment(WriteSheetHolder writeSheetHolder, Cell cell, String commentText) {
        Sheet sheet = writeSheetHolder.getSheet();
        Workbook workbook = sheet.getWorkbook();
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        CreationHelper factory = workbook.getCreationHelper();

        // 创建批注锚点
        ClientAnchor anchor = factory.createClientAnchor();
        anchor.setCol1(cell.getColumnIndex());
        anchor.setCol2(cell.getColumnIndex() + 1);
        anchor.setRow1(cell.getRowIndex());
        anchor.setRow2(cell.getRowIndex() + 3);
        anchor.setDx1(0);
        anchor.setDy1(0);
        anchor.setDx2(0);
        anchor.setDy2(0);

        // 创建批注并设置内容
        Comment comment = drawing.createCellComment(anchor);
        RichTextString richTextString = new XSSFRichTextString(commentText);
        comment.setString(richTextString);
        cell.setCellComment(comment);
    }
}
