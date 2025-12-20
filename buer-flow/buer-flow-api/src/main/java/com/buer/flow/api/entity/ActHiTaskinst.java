package com.buer.flow.api.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 历史任务
 *
 * @author zoulan
 * @since 2023-05-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "历史任务")
@Table("act_hi_taskinst")
public class ActHiTaskinst extends Model<ActHiTaskinst> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
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
     * 任务流程图定义key
     */
    @Schema(description = "任务流程图定义key")
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
     * 租户id
     */
    @Schema(description = "租户id")
    @Column("tenant_id_")
    private String tenantId;


}