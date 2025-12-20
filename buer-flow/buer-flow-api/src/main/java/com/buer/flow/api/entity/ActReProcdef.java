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

/**
 * 流程定义
 *
 * @author zoulan
 * @since 2024-06-09
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "流程定义")
@Table("act_re_procdef")
public class ActReProcdef extends Model<ActReProcdef> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 流程定义id
     */
    @Id
    @Schema(description = "id")
    @Column(value = "id_")
    private String id;

    /**
     * 数据版本,乐观锁
     */
    @Schema(description = "数据版本,乐观锁")
    @Column("rev_")
    private Integer rev;

    /**
     * 类别,自动生成的
     */
    @Schema(description = "类别,自动生成的")
    @Column("category_")
    private String category;

    /**
     * 流程图定义的name
     */
    @Schema(description = "流程图定义的name")
    @Column("name_")
    private String name;

    /**
     * 流程图定义的id
     */
    @Schema(description = "流程图定义的id")
    @Column("key_")
    private String key;

    /**
     * 流程的版本
     */
    @Schema(description = "流程的版本")
    @Column("version_")
    private Integer version;

    /**
     * 管理流程部署的ID
     */
    @Schema(description = "管理流程部署的ID")
    @Column("deployment_id_")
    private String deploymentId;

    /**
     * bpmn文件名称
     */
    @Schema(description = "bpmn文件名称")
    @Column("resource_name_")
    private String resourceName;

    /**
     * 图片名称
     */
    @Schema(description = "图片名称")
    @Column("dgrm_resource_name_")
    private String dgrmResourceName;

    /**
     * 描述
     * 自动读取模型process元素中的description属性
     */
    @Schema(description = "描述")
    @Column("description_")
    private String description;

    /**
     * 是否从key启动，0否 1是
     */
    @Schema(description = "是否从key启动，0否1是")
    @Column("has_start_form_key_")
    private Byte hasStartFormKey;

    /**
     *
     */
    @Schema(description = "")
    @Column("has_graphical_notation_")
    private Byte hasGraphicalNotation;

    /**
     * 是否挂起，1激活 2挂起
     */
    @Schema(description = "是否挂起，1激活 2挂起")
    @Column("suspension_state_")
    private Integer suspensionState;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    @Column("tenant_id_")
    private String tenantId;

    /**
     * 所属流程引擎版本
     */
    @Schema(description = "所属流程引擎版本")
    @Column("engine_version_")
    private String engineVersion;

    /**
     * app版本
     */
    @Schema(description = "app版本")
    @Column("app_version_")
    private Integer appVersion;

}