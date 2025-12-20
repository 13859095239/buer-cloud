package com.buer.system.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典项 VO
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Data
@Schema(description = "字典项")
public class DictItemVO implements Serializable {

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 字典标识
     */
    @Schema(description = "字典标识")
    private String dictKey;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 值
     */
    @Schema(description = "值")
    private String value;

    /**
     * 主题
     */
    @Schema(description = "主题")
    private String theme;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Object sort;

}