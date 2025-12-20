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
 * Flowable 历史任务候选人/参与者信息表 (act_hi_identitylink)
 * 用于存储已完成任务的用户/用户组参与信息
 *
 * @author zoulan
 * @since 2025-08-16
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "编号规则")
@Table("act_hi_identitylink")
public class ActHiIdentitylink extends Model<ActHiIdentitylink> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @Id
    @Column("ID_")
    @Schema(description = "id")
    private String id;

    /**
     * 用户组 ID（如果是候选组时使用）
     */
    @Column("GROUP_ID_")
    @Schema(description = "用户组 ID（如果是候选组时使用）")
    private String groupId;

    /**
     * 类型
     * assignee=办理人
     * candidate=候选人
     * participant=参与者
     * owner=拥有人
     * starter=发起人
     */
    @Column("TYPE_")
    @Schema(description = "类型（candidate=候选人，participant=参与者，assignee=办理人 等）")
    private String type;

    /**
     * 用户 ID（如果是候选用户时使用）
     */
    @Column("USER_ID_")
    @Schema(description = "")
    private String userId;

    /**
     * 任务 ID，关联 act_hi_taskinst
     */
    @Column("TASK_ID_")
    @Schema(description = "任务 ID，关联 act_hi_taskinst")
    private String taskId;

    /**
     * 创建时间
     */
    @Column("CREATE_TIME_")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 流程实例 ID，关联 act_hi_procinst
     */
    @Column("PROC_INST_ID_")
    @Schema(description = "流程实例 ID，关联 act_hi_procinst")
    private String procInstId;

    /**
     * 作用域 ID（Scope，例如 CMMN/扩展模型使用）
     */
    @Column("SCOPE_ID_")
    @Schema(description = "作用域 ID（Scope，例如 CMMN/扩展模型使用） ")
    private String scopeId;

    /**
     * 子作用域 ID（SubScope，例如 Case 实例的子作用域）
     */
    @Column("SUB_SCOPE_ID_")
    @Schema(description = "子作用域 ID（SubScope，例如 Case 实例的子作用域）")
    private String subScopeId;

    /**
     * 作用域类型（ScopeType：bpmn、cmmn 等）
     */
    @Column("SCOPE_TYPE_")
    @Schema(description = "作用域类型（ScopeType：bpmn、cmmn 等）")
    private String scopeType;

    /**
     * 作用域定义 ID（Scope Definition Id）
     */
    @Column("SCOPE_DEFINITION_ID_")
    @Schema(description = "作用域定义 ID（Scope Definition Id）")
    private String scopeDefinitionId;
}