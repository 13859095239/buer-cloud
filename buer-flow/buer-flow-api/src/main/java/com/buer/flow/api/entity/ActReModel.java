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
 * 流程模型
 *
 * @author zoulan
 * @since 2023-05-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "流程模型")
@Table("act_re_model")
public class ActReModel extends Model<ActReModel> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 流程模型id
     */
    @Id
    @Schema(description = "流程模型id")
    @Column("id_")
    private String id;

    /**
     * 数据版本,乐观锁
     */
    @Schema(description = "数据版本,乐观锁")
    @Column("rev_")
    private Integer rev;

    /**
     * 模型名称
     */
    @Schema(description = "模型名称")
    @Column("name_")
    private String name;

    /**
     * 模型key
     */
    @Schema(description = "模型key")
    @Column("key_")
    private String key;

    /**
     * 类型
     */
    @Schema(description = "类型")
    @Column("category_")
    private String category;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @Column(value = "create_time_")
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    @Schema(description = "最后更新时间")
    @Column("last_update_time_")
    private LocalDateTime lastUpdateTime;

    /**
     * 当前版本
     */
    @Schema(description = "当前版本")
    @Column("version_")
    private Integer version;

    /**
     * 扩展属性
     */
    @Schema(description = "扩展属性")
    @Column("meta_info_")
    private String metaInfo;

    /**
     * 最新部署id，外键关联act_re_deployment表
     */
    @Schema(description = "最新部署id")
    @Column("deployment_id_")
    private String deploymentId;
    /**
     * bpmn资源id，外键关联act_ge_bytearray表
     */
    @Schema(description = "外键关联act_ge_bytearray表的bpmn资源ID")
    @Column("editor_source_value_id_")
    private String editorSourceValueId;

    /**
     * png资源id，外键关联act_ge_bytearray表
     */
    @Schema(description = "png资源id，外键关联act_ge_bytearray表")
    @Column("editor_source_extra_value_id_")
    private String editorSourceExtraValueId;

    /**
     * 租户id
     */
    @Schema(description = "租户id")
    @Column("tenant_id_")
    private String tenantId;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String depiction;

    /**
     * 0-正常，1-锁定
     */
    @Schema(description = "0-正常，1-锁定")
    private String lockFlag;

    /**
     * 0-正常，1-删除
     */
    @Schema(description = "0-正常，1-删除")
    private String delFlag;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Double sort;

    /**
     * web路径
     */
    @Schema(description = "web路径")
    private String webPath;

    /**
     * 后端API路径
     */
    @Schema(description = "后端API路径")
    private String apiPath;

    /**
     * 编号规则id
     */
    @Schema(description = "编号规则id")
    private Long codeRuleId;

    /**
     * 最后发布时间
     */
    @Schema(description = "最后发布时间")
    private LocalDateTime lastDeployTime;


    /**
     * 流程版本，最后发布版本
     */
    @Schema(description = "流程版本，最后发布版本")
    private Integer lastDeployVersion;

}