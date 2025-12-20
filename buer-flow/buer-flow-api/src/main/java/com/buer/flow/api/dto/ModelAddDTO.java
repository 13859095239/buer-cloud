package com.buer.flow.api.dto;

import com.buer.common.core.validation.annotation.FlowableKey;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 新增模型DTO DTO
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Accessors(chain = true)
@Schema(description = "新增模型DTO")
public class ModelAddDTO implements Serializable {

    /**
     * 模型key
     */
    @Schema(description = "模型key")
    @FlowableKey
    private String key;

    /**
     * 模型名称
     */
    @Schema(description = "模型名称")
    private String name;

    /**
     * 流程类型
     */
    @Schema(description = "流程类型")
    private String category;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String depiction;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Double sort;

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

}
