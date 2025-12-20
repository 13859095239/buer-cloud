package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 公共参数配置 Query
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Schema(description = "公共参数配置Query")
public class ConfigQuery implements Serializable {

    /**
     * 公共参数配置id列表
     */
    @Schema(description = "公共参数配置id列表")
    private String configIds;

    /**
     * 参数键名
     */
    @Schema(description = "参数键名")
    private String configKey;

    /**
     * 参数名称
     */
    @Schema(description = "参数名称")
    private String name;
}