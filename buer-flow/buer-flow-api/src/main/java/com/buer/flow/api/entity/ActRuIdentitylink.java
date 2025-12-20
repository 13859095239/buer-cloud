package com.buer.flow.api.entity;

import com.mybatisflex.annotation.Column;
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
 * 待办任务的候选人/组
 *
 * @author zoulan
 * @since 2025-09-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "待办任务的候选人/组")
@Table("act_ru_identitylink")
public class ActRuIdentitylink extends Model<ActRuIdentitylink> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 待办任务的候选人/组 ID
     */
    @Column("id_")
    private String id;

    /**
     * 任务 ID
     */
    @Column("task_id_")
    private String taskId;

    /**
     * 流程实例 ID
     */
    @Column("proc_inst_id_")
    private String procInstId;

    /**
     * 流程定义 ID
     */
    @Column("proc_def_id_")
    private String procDefId;

    /**
     * 用户 ID（候选人）
     */
    @Column("user_id_")
    private String userId;

    /**
     * 组 ID（候选组）
     */
    @Column("group_id_")
    private String groupId;

    /**
     * 类型，例如 candidate、assignee 等
     */
    @Column("type_")
    private String type;

    /**
     * 租户 ID，可选
     */
    @Column("tenant_id_")
    private String tenantId;

    /**
     * 创建时间
     */
    @Column("create_time_")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column("update_time")
    private LocalDateTime updateTime;
}
