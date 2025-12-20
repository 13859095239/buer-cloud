package com.buer.flow.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 模型类型VO
 *
 * @author zoulan
 * @since 2023-05-12
 */
@Data
@Schema(description = "模型类型")
public class CategoryVO implements Serializable {

    /**
     *
     */
    @Schema(description = "")
    private Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 是否允许删除
     */
    @Schema(description = "是否允许删除")
    private String isAllowDel;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Object sort;

}