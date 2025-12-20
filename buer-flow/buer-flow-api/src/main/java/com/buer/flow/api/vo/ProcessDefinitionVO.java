package com.buer.flow.api.vo;

import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 流程定义VO
 *
 * @author zoulan
 * @since 2024-06-09
 */
@Data
@Accessors(chain = true)
@Schema(description = "流程定义VO")
public class ProcessDefinitionVO implements Serializable {

    /**
     * 流程定义id
     */
    @Schema(description = "id")
    private String id;

    /**
     * 管理流程部署的ID
     */
    @Schema(description = "管理流程部署的ID")
    private String deploymentId;

    /**
     * 流程的版本
     */
    @Schema(description = "流程的版本")
    private Integer version;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;
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
     * 流程模型id
     */
    @Schema(description = "流程模型id")
    private String modelId;

    /**
     * 流程模型名称
     */
    @Schema(description = "流程模型名称")
    private String modelName;

    /**
     * 发布时间
     */
    @Schema(description = "发布时间")
    private String deployTime;


}