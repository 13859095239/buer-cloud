package com.buer.flow.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 创建流程后返回流程实例VO
 *
 * @author zoulan
 * @since 2024-06-07
 */
@Data
@Accessors(chain = true)
@Schema(description = "创建流程后返回流程实例VO")
public class CreatedProcinstVO implements Serializable {

    /**
     * 流程实例id
     */
    @Schema(description = "流程实例id")
    private String procInstId;
    /**
     * 流水号
     */
    @Schema(description = "流水号")
    private String businessKey;

    /**
     * 流水号
     */
    @Schema(description = "流程状态")
    private String procStatus;

    /**
     * 流程开始时间
     */
    @Schema(description = "流程开始时间")
    private LocalDateTime procStartTime;

    /**
     * 流程结束时间
     */
    @Schema(description = "流程结束时间")
    private LocalDateTime procEndTime;


}
