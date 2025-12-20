package com.buer.flow.api.vo;

import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 历史任务 VO
 *
 * @author zoulan
 * @since 2023-05-11
 */
@Data
@Schema(description = "历史任务 VO")
public class TaskinstVO implements Serializable {

    /**
     * id
     */
    @Schema(description = "id")
    @Column(value = "id_")
    private String id;

    /**
     * 流程定义id
     */
    @Schema(description = "流程定义id")
    @Column("proc_def_id_")
    private String procDefId;

    /**
     * 任务的key，与流程图中的id对应
     */
    @Schema(description = "任务的key，与流程图中的id对应")
    @Column("task_def_key_")
    private String taskDefKey;

    /**
     * 流程实例id
     */
    @Schema(description = "流程实例id")
    @Column("proc_inst_id_")
    private String procInstId;

    /**
     * 所属执行实例id
     */
    @Schema(description = "所属执行实例id")
    @Column("execution_id_")
    private String executionId;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    @Column("name_")
    private String name;

    /**
     * 父任务id
     */
    @Schema(description = "父任务id")
    @Column("parent_task_id_")
    private String parentTaskId;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @Column("description_")
    private String description;

    /**
     * 任务拥有者
     */
    @Schema(description = "任务拥有者")
    @Column("owner_")
    private String owner;

    /**
     * 任务办理人
     */
    @Schema(description = "任务办理人")
    @Column("assignee_")
    private String assignee;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @Column("start_time_")
    private LocalDateTime startTime;

    /**
     * 任务被拾取时间
     */
    @Schema(description = "任务被拾取时间")
    @Column("claim_time_")
    private LocalDateTime claimTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @Column("end_time_")
    private LocalDateTime endTime;

    /**
     * 总耗时(毫秒)
     */
    @Schema(description = "总耗时(毫秒)")
    @Column("duration_")
    private Long duration;

    /**
     * 删除原因
     */
    @Schema(description = "删除原因")
    @Column("delete_reason_")
    private String deleteReason;

    /**
     * 优先级
     */
    @Schema(description = "优先级")
    @Column("priority_")
    private Integer priority;

    /**
     * 办理时间
     */
    @Schema(description = "办理时间")
    @Column("due_date_")
    private LocalDateTime dueDate;

    /**
     * 表单key
     */
    @Schema(description = "表单key")
    @Column("form_key_")
    private String formKey;

    /**
     * 任务分类
     */
    @Schema(description = "任务分类")
    @Column("category_")
    private String category;

    /**
     * 流水号
     */
    @Schema(description = "流水号")
    private String businessKey;

    /**
     * 流程实例名称
     */
    @Schema(description = "流程实例名称")
    private String procInstName;

    /**
     * 模型key
     */
    @Schema(description = "模型key")
    private String modelKey;

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
     * 任务创建人id
     */
    @Schema(description = "任务创建人")
    private String startUserId;

    /**
     * 任务创建人
     */
    @Schema(description = "任务创建人")
    private String startUserName;
}