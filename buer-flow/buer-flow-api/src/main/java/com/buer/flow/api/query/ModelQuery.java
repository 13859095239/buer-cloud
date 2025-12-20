package com.buer.flow.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 模型 Query
 *
 * @author zoulan
 * @since 2023-05-11
 */
@Data
@Accessors(chain = true)
@Schema(description = "Query")
public class ModelQuery implements Serializable {

    /**
     * id列表
     */
    @Schema(description = "id列表")
    private List<Long> ids;

    /**
     * 模型名称
     */
    @Schema(description = "模型名称")
    private String name;

    /**
     * 模型key
     */
    @Schema(description = "模型key")
    private String key;

    /**
     * 流程类型
     */
    @Schema(description = "流程类型")
    private String category;

}