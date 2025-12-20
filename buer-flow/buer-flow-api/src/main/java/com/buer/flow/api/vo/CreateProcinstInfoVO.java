package com.buer.flow.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 创建实例信息 VO
 *
 * @author zoulan
 * @since 2025-08-23
 */
@Data
@Accessors(chain = true)
@Schema(description = "创建实例信息 VO")
public class CreateProcinstInfoVO implements Serializable {

    /**
     * 流程定义id
     */
    @Schema(description = "流程定义id")
    private String procDefId;

    /**
     * 模型 key
     */
    @Schema(description = "模型 key")
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


}
