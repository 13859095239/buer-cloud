package com.buer.flow.api.vo;

import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 待办任务 VO
 *
 * @author zoulan
 * @since 2023-05-11
 */
@Data
@Accessors(chain = true)
@Schema(description = "待办任务 VO")
public class TaskVO implements Serializable {

    /**
     * 待办任务 id
     */
    @Column(value = "id_")
    @Schema(description = "id")
    private String id;

    /**
     * 数据版本,乐观锁
     */
    @Schema(description = "数据版本,乐观锁")
    @Column("rev_")
    private Integer rev;

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
     * 任务名称
     */
    @Schema(description = "任务名称")
    @Column("name_")
    private String name;

    /**
     * 父任务ID
     */
    @Schema(description = "父任务ID")
    @Column("parent_task_id_")
    private String parentTaskId;

    /**
     * 任务描述，对应画图时的document。
     * 可以将document的值设置成UEL表达式，动态设置描述，例如待办/已办任务的自定义标题
     */
    @Schema(description = "任务描述")
    @Column("description_")
    private String description;

    /**
     * 任务的key，与流程图中的id对应
     */
    @Schema(description = "任务的key，与流程图中的id对应")
    @Column("task_def_key_")
    private String taskDefKey;

    /**
     * 任务的拥有者
     */
    @Schema(description = "任务的拥有者")
    @Column("owner_")
    private String owner;

    /**
     * 任务的办理人
     */
    @Schema(description = "任务的办理人")
    @Column("assignee_")
    private String assignee;

    /**
     * 任务委托状态
     * 任务被委托时，为PENDING，委托任务被解决后为RESOLVED
     */
    @Schema(description = "任务委托状态")
    @Column("delegation_")
    private String delegation;

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
    private LocalDateTime createTime;

    /**
     * 办理期限
     */
    @Schema(description = "办理期限")
    @Column("due_date_")
    private LocalDateTime dueDate;

    /**
     * 任务类别
     */
    @Schema(description = "任务类别")
    @Column("category_")
    private String category;

    /**
     * 挂起状态，1激活，2挂起
     */
    @Schema(description = "挂起状态，1激活，2挂起")
    @Column("suspension_state_")
    private Integer suspensionState;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    @Column("tenant_id_")
    private String tenantId;

    /**
     * formKey
     */
    @Schema(description = "formKey")
    @Column("form_key_")
    private String formKey;

    /**
     * 任务被拾取的时间
     */
    @Schema(description = "任务被拾取的时间")
    @Column("claim_time_")
    private LocalDateTime claimTime;

    /**
     * app版本
     */
    @Schema(description = "app版本")
    @Column("app_version_")
    private Integer appVersion;

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