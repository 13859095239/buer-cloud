package com.buer.flow.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 创建流程 DTO
 *
 * @author zoulan
 * @since 2024-06-07
 */
@Data
@Accessors(chain = true)
@Schema(description = "创建流程DTO")
public class CreateProcinstDTO implements Serializable {

    /**
     * 模型key
     */
    @Schema(description = "模型key")
    private String modelKey;
}
