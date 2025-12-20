package com.buer.flow.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 流程定义Query
 *
 * @author zoulan
 * @since 2024-06-09
 */
@Data
@Schema(description = "流程定义Query")
public class ProcessDefinitionQuery implements Serializable {

    /**
     * 模型key
     */
    @Schema(description = "模型key")
    private String modelKey;

}