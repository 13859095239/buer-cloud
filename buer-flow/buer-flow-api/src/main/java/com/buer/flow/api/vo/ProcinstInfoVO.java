package com.buer.flow.api.vo;

import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程实例信息 VO
 * 用于查看流程实例信息
 * 该类是流程办理的基类,待办任务、已办任务VO继承该类。
 *
 * @author zoulan
 * @since 2025-08-23
 */
@Data
@Accessors(chain = true)
@Schema(description = "流程实例信息 VO")
public class ProcinstInfoVO implements Serializable {

    /**
     * 执行实例ID
     */
    @Schema(description = "执行实例ID")
    @Column("execution_id_")
    private String executionId;

    /**
     * 流程实例ID
     */
    @Schema(description = "流程实例ID")
    @Column("proc_inst_id_")
    private String procInstId;

    /**
     * 流程定义ID
     */
    @Schema(description = "流程定义ID")
    @Column("proc_def_id_")
    private String procDefId;

    /**
     * 优先级，默认为50
     */
    @Schema(description = "优先级，默认为50")
    @Column("priority_")
    private Integer priority;

    /**
     * 流程实例开始时间
     */
    @Schema(description = "流程实例开始时间")
    @Column("start_time_")
    private LocalDateTime procInstStartTime;

    /**
     * 流程实例开始时间
     */
    @Schema(description = "流程实例结束时间")
    @Column("ent_time_")
    private LocalDateTime procInstEndTime;

    /**
     * 流水号
     */
    @Schema(description = "流水号")
    @Column("business_key_")
    private String businessKey;

    /**
     * 流程实例名称
     */
    @Schema(description = "流程实例名称")
    private String procInstName;

    /**
     * 模型名称
     */
    @Schema(description = "模型名称")
    private String modelName;

    /**
     * 模型web路径
     */
    @Schema(description = "模型web路径")
    private String modelWebPath;

    /**
     * 操作列表
     */
    @Schema(description = "操作列表")
    private String operationList;
}
