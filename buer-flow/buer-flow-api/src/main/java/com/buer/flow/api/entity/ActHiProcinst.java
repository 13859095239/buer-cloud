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
 * 流程实例
 *
 * @author zoulan
 * @since 2023-05-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "流程实例")
@Table("act_hi_procinst")
public class ActHiProcinst extends Model<ActHiProcinst> implements Serializable {

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
     * 流程实例ID
     */
    @Schema(description = "流程实例ID")
    @Column("proc_inst_id_")
    private String procInstId;

    /**
     * 实例名称
     */
    @Schema(description = "实例名称")
    @Column("name_")
    private String name;

    /**
     * 流水号
     */
    @Schema(description = "流水号")
    @Column("business_key_")
    private String businessKey;

    /**
     * 流程定义id
     */
    @Schema(description = "流程定义id")
    @Column("proc_def_id_")
    private String procDefId;

    /**
     * 流程实例开始时间
     */
    @Schema(description = "流程实例开始时间")
    @Column("start_time_")
    private LocalDateTime startTime;

    /**
     * 程实例结束时间
     */
    @Schema(description = "流程实例结束时间")
    @Column("end_time_")
    private LocalDateTime endTime;

    /**
     * 流程总耗时(毫秒)
     */
    @Schema(description = "总耗时(毫秒)")
    @Column("duration_")
    private Long duration;

    /**
     * 流程创建者id
     */
    @Schema(description = "开始用户id")
    @Column("start_user_id_")
    private String startUserId;

    /**
     * 开始节点ID
     */
    @Schema(description = "开始节点ID")
    @Column("start_act_id_")
    private String startActId;

    /**
     * 结束节点ID
     */
    @Schema(description = "结束节点ID")
    @Column("end_act_id_")
    private String endActId;

    /**
     * 上级流程实例ID
     */
    @Schema(description = "上级流程实例ID")
    @Column("super_process_instance_id_")
    private String superProcessInstanceId;

    /**
     * 删除原因
     */
    @Schema(description = "删除原因")
    @Column("delete_reason_")
    private String deleteReason;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    @Column("tenant_id_")
    private String tenantId;

}