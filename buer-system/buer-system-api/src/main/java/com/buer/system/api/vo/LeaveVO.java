package com.buer.system.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 请假流程demo VO
 *
 * @author zoulan
 * @since 2024-06-07
 */
@Data
@Schema(description = "请假流程demo")
public class LeaveVO implements Serializable {

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
     * 请假人id
     */
    @Schema(description = "请假人id")
    private Long leaveUserId;
    /**
     * 请假人
     */
    @Schema(description = "请假人")
    private String leaveUserName;

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
     * 流水号
     */
    @Schema(description = "流水号")
    private String procBusinessKey;

    /**
     * 流程状态
     */
    @Schema(description = "流程状态")
    private String procStatus;

    /**
     * 流程创建人
     */
    @Schema(description = "流程创建人")
    private Long procStartUserId;

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