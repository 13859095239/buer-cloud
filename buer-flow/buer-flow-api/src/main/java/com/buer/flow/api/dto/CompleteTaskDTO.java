package com.buer.flow.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * 完成任务 DTO
 *
 * @author zoulan
 * @since 2024-06-07
 */
@Data
@Accessors(chain = true)
@Schema(description = "完成任务 DTO")
public class CompleteTaskDTO implements Serializable {

    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private String taskId;

    /**
     * 办理意见
     */
    @Schema(description = "办理意见")
    private String remark;

    /**
     * 流程变量
     */
    @Schema(description = "流程变量")
    private Map<String, Object> variables = Collections.emptyMap();

    /**
     * 是否局部流程变量
     */
    @Schema(description = "是否局部流程变量")
    private Map<String, Object> localScope = Collections.emptyMap();

}
