package com.buer.flow.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 模型更新 DTO
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Accessors(chain = true)
@Schema(description = "模型DTO")
public class ModelUpdateDTO implements Serializable {

    /**
     * 模型id
     */
    @Schema(description = "模型id")
    private String id;

    /**
     * 模型名称
     */
    @Schema(description = "模型名称")
    private String name;

    /**
     * web路径
     */
    @Schema(description = "web路径")
    private String webPath;

    /**
     * 编号规则id
     */
    @Schema(description = "编号规则id")
    private Long codeRuleId;

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


}
