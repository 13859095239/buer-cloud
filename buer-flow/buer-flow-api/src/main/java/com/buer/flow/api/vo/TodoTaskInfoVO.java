package com.buer.flow.api.vo;

import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 办理任务信息 VO
 * 用于查看待办任务信息
 *
 * @author zoulan
 * @since 2025-08-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Schema(description = "办理任务信息 VO")
public class TodoTaskInfoVO extends ProcinstInfoVO {

    /**
     * 待办任务id
     */
    @Schema(description = "taskId")
    private String taskId;

    /**
     * 执行实例ID
     */
    @Schema(description = "执行实例ID")
    @Column("execution_id_")
    private String executionId;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    @Column("name_")
    private String taskName;

    /**
     * 父任务ID
     */
    @Schema(description = "父任务ID")
    @Column("parent_task_id_")
    private String parentTaskId;

    /**
     * 优先级，默认为50
     */
    @Schema(description = "优先级，默认为50")
    @Column("priority_")
    private Integer priority;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @Column("create_time_")
    private LocalDateTime taskCreateTime;

    /**
     * 办理期限
     */
    @Schema(description = "办理期限")
    @Column("due_date_")
    private LocalDateTime taskDueDate;

    /**
     * 任务的key，与流程图中的id对应
     */
    @Schema(description = "任务的key，与流程图中的id对应")
    @Column("task_def_key_")
    private String taskDefKey;

}