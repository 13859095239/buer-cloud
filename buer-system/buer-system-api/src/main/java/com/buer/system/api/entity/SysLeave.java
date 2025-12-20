package com.buer.system.api.entity;

import com.buer.common.core.entity.SuperEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 请假 Entity
 *
 * @author zoulan
 * @since 2024-06-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "请假")
@Table("sys_leave")
public class SysLeave extends SuperEntity<SysLeave> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
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
     * 描述
     */
    @Schema(description = "描述")
    private String depiction;

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