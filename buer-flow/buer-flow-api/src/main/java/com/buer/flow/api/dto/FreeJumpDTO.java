package com.buer.flow.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 自由跳转 DTO
 *
 * @author zoulan
 * @since 2024-06-14
 */
@Data
@Accessors(chain = true)
@Schema(description = "自由跳转DTO")
public class FreeJumpDTO implements Serializable {

    /**
     * 当前待办任务Id
     */
    @Schema(description = "当前待办任务Id")
    private String sourceTaskId;

    /**
     * 跳转目标任务的定义标识
     */
    @Schema(description = "跳转目标任务的定义标识")
    private String targetTaskDefKey;

    /**
     * 跳转注释说明
     */
    @Schema(description = "跳转注释说明")
    private String taskComment;

}
