package com.buer.flow.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程委托 DTO
 *
 * @author zoulan
 * @since 2024-06-13
 */
@Data
@Schema(description = "委托")
public class DelegateDTO implements Serializable {

    /**
     * 委托ID
     */
    @Schema(description = "委托ID")
    private Long id;

    /**
     * 被委托人
     */
    @Schema(description = "被委托人")
    private Long userId;

    /**
     * 委托人
     */
    @Schema(description = "委托人")
    private Long toUserId;

    /**
     * 委托开始时间
     */
    @Schema(description = "委托开始时间")
    private LocalDateTime beginTime;

    /**
     * 委托结束时间
     */
    @Schema(description = "委托结束时间")
    private LocalDateTime endTime;

    /**
     * 委托流程（为空是全部流程）
     */
    @Schema(description = "委托流程（为空是全部流程）")
    private String modelId;

    /**
     * 委托原因
     */
    @Schema(description = "委托原因")
    private String depiction;

}