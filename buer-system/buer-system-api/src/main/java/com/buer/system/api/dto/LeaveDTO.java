package com.buer.system.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 请假 DTO
 *
 * @author zoulan
 * @since 2024-06-07
 */
@Data
@Schema(description = "请假流程demo")
public class LeaveDTO<T> implements Serializable {

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 流程实例id
     */
    @Schema(description = "流程实例id")
    private String procInstId;

    /**
     * 请假开始时间
     */
    @Schema(description = "请假开始时间")
    private LocalDateTime leaveBeginTime;

    /**
     * 请假结束时间
     */
    @Schema(description = "请假结束时间")
    private LocalDateTime leaveEndTime;

    /**
     * 请假人
     */
    @Schema(description = "请假人")
    private Long leaveUser;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String depiction;

    /**
     * 编号
     */
    @Schema(description = "编号")
    private String businessKey;

    /**
     * 流程状态
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

    /**
     * 流程结束时间
     */
    @Schema(description = "流程结束时间")
    private T flowData;
}