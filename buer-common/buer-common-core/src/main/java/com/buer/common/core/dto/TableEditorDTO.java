package com.buer.common.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 岗位表格编辑对象
 *
 * @author zoulan
 * @since 2024-05-01
 */
@Data
@Accessors(chain = true)
@Schema(description = "表格编辑对象")
public class TableEditorDTO<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 新增列表
     */
    @Schema(description = "新增列表")
    private List<T> addList;

    /**
     * 更新列表
     */
    @Schema(description = "更新列表")
    private List<T> updateList;

    /**
     * 删除keys
     */
    @Schema(description = "删除keys")
    private String deleteKeys;

}
