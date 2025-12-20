package com.buer.flow.api.vo;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 模型 VO
 *
 * @author zoulan
 * @since 2023-05-11
 */
@Data
@Schema(description = "模型VO")
public class ModelVO implements Serializable {

    /**
     * 流程模型id
     */
    @Id
    @Schema(description = "流程模型id")
    @Column("id_")
    private String id;

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
     * 编号规则id
     */
    @Schema(description = "编号规则id")
    private Long codeRuleId;

    /**
     * 当前版本
     */
    @Schema(description = "当前版本")
    @Column("version_")
    private Integer version;

    /**
     * 流程版本，最后发布版本
     */
    @Schema(description = "流程版本，最后发布版本")
    private Integer lastDeployVersion;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    @Schema(description = "最后更新时间")
    @Column("last_update_time_")
    private LocalDateTime lastUpdateTime;

    /**
     * 最后发布时间
     */
    @Schema(description = "最后发布时间")
    private LocalDateTime lastDeployTime;

    /**
     * 流程类型名称
     */
    @Schema(description = "流程类型名称")
    private String categoryName;

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
     * 排序
     */
    @Schema(description = "排序")
    private Double sort;

}