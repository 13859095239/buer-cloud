package com.buer.flow.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 完成流程任务后返回VO
 *
 * @author zoulan
 * @since 2024-06-07
 */
@Data
@Accessors(chain = true)
@Schema(description = "创建流程后返回流程实例VO")
public class CompletedTaskVO implements Serializable {

    /**
     * 流程实例id
     */
    @Schema(description = "流程实例id")
    private String procInstId;
    /**
     * 任务id
     */
    @Schema(description = "流程实例id")
    private String taskId;

}
