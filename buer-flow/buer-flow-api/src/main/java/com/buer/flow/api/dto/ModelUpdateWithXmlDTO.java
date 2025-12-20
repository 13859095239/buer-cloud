package com.buer.flow.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 模型更新，包含xml DTO
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Accessors(chain = true)
@Schema(description = "模型更新，包含xml DTO")
public class ModelUpdateWithXmlDTO implements Serializable {

    /**
     * modelId
     */
    @Schema(description = "modelId")
    private String id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 模型key
     */
    @Schema(description = "key")
    private String key;

    /**
     * category
     */
    @Schema(description = "category")
    private String category;

    /**
     * description
     */
    @Schema(description = "description")
    private String description;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Double sort;

    /**
     * 模型xml
     */
    @Schema(description = "模型xml")
    private String modelXml;
    /**
     * svgXml
     */
    @Schema(description = "svgXml")
    private String svgXml;
}
