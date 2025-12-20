package com.buer.flow.api.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Flowable 7 运行时执行实例表 ACT_RU_EXECUTION
 * 映射全部字段，带详细中文注释
 */
@Table("act_ru_execution")
public class ActRuExecution extends Model<ActRuExecution> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 执行实例 ID
     */
    @Id
    @Column("ID_")
    @Schema(description = "id")
    private String id;

    /**
     * 乐观锁版本号，每次更新 +1
     */
    @Column("REV_")
    private Integer rev;

    /**
     * 流程实例 ID（根 execution ID）
     */
    @Column("PROC_INST_ID_")
    private String procInstId;

    /**
     * 业务主键（业务系统传入，用于关联业务对象）
     */
    @Column("BUSINESS_KEY_")
    private String businessKey;

    /**
     * 父执行实例 ID（分支/子执行的父级）
     */
    @Column("PARENT_ID_")
    private String parentId;

    /**
     * 流程定义 ID
     */
    @Column("PROC_DEF_ID_")
    private String procDefId;

    /**
     * 父流程执行 ID（子流程调用场景）
     */
    @Column("SUPER_EXEC_")
    private String superExec;

    /**
     * 根流程实例 ID（嵌套子流程时使用）
     */
    @Column("ROOT_PROC_INST_ID_")
    private String rootProcInstId;

    /**
     * 当前活动节点 ID（BPMN flowNodeId）
     */
    @Column("ACT_ID_")
    private String actId;

    /**
     * 是否处于活动状态（1=活动，0=挂起/结束）
     */
    @Column("IS_ACTIVE_")
    private Integer isActive;

    /**
     * 是否并发执行流（1=是，0=否）
     */
    @Column("IS_CONCURRENT_")
    private Integer isConcurrent;

    /**
     * 是否作用域执行（Scope Execution）
     */
    @Column("IS_SCOPE_")
    private Integer isScope;

    /**
     * 是否事件作用域执行（Event Subprocess）
     */
    @Column("IS_EVENT_SCOPE_")
    private Integer isEventScope;

    /**
     * 是否多实例根执行（Multi-Instance Root Execution）
     */
    @Column("IS_MI_ROOT_")
    private Integer isMiRoot;

    /**
     * 挂起状态（1=激活，2=挂起）
     */
    @Column("SUSPENSION_STATE_")
    private Integer suspensionState;

    /**
     * 缓存的实体状态（内部使用）
     */
    @Column("CACHED_ENT_STATE_")
    private Integer cachedEntState;

    /**
     * 租户 ID（多租户场景使用）
     */
    @Column("TENANT_ID_")
    private String tenantId;

    /**
     * 执行实例名称
     */
    @Column("NAME_")
    private String name;

    /**
     * 启动节点 ID（Execution 启动时的节点）
     */
    @Column("START_ACT_ID_")
    private String startActId;

    /**
     * 启动时间
     */
    @Column("START_TIME_")
    private LocalDateTime startTime;

    /**
     * 启动用户 ID
     */
    @Column("START_USER_ID_")
    private String startUserId;

    /**
     * 锁定时间（并发控制使用）
     */
    @Column("LOCK_TIME_")
    private LocalDateTime lockTime;

    /**
     * 是否启用计数（性能优化，缓存子任务/事件订阅数量）
     */
    @Column("IS_COUNT_ENABLED_")
    private Integer isCountEnabled;

    /**
     * 事件订阅数量
     */
    @Column("EVT_SUBSCR_COUNT_")
    private Integer evtSubscrCount;

    /**
     * 当前任务数量
     */
    @Column("TASK_COUNT_")
    private Integer taskCount;

    /**
     * 当前作业数量
     */
    @Column("JOB_COUNT_")
    private Integer jobCount;

    /**
     * 定时器作业数量
     */
    @Column("TIMER_JOB_COUNT_")
    private Integer timerJobCount;

    /**
     * 挂起的作业数量
     */
    @Column("SUSP_JOB_COUNT_")
    private Integer suspJobCount;

    /**
     * 死信作业数量
     */
    @Column("DEADLETTER_JOB_COUNT_")
    private Integer deadLetterJobCount;

    /**
     * 变量数量
     */
    @Column("VAR_COUNT_")
    private Integer varCount;

    /**
     * IdentityLink 数量（参与人/候选人缓存）
     */
    @Column("ID_LINK_COUNT_")
    private Integer idLinkCount;

    /**
     * 回调 ID（外部系统回调标识）
     */
    @Column("CALLBACK_ID_")
    private String callbackId;

    /**
     * 回调类型（外部系统回调类型）
     */
    @Column("CALLBACK_TYPE_")
    private String callbackType;

    /**
     * 外部引用 ID（例如 Case、外部系统 ID）
     */
    @Column("REFERENCE_ID_")
    private String referenceId;

    /**
     * 外部引用类型
     */
    @Column("REFERENCE_TYPE_")
    private String referenceType;

    /**
     * 业务状态（业务系统同步过来的状态信息）
     */
    @Column("BUSINESS_STATUS_")
    private String businessStatus;

    /**
     * 锁拥有者（External Worker 场景使用）
     */
    @Column("LOCK_OWNER_")
    private String lockOwner;

    /**
     * 外部 Worker 作业计数
     */
    @Column("EXTERNAL_WORKER_JOB_COUNT_")
    private Integer externalWorkerJobCount;

}