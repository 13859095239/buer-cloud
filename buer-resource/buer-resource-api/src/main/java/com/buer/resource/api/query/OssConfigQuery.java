package com.buer.resource.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 对象存储配置表Query
 *
 * @author zoulan
 * @since 2023-08-27
 */
@Data
@Schema(description = "对象存储配置表Query")
public class OssConfigQuery implements Serializable {

    /**
     * 配置key
     */
    @Schema(description = "配置key")
    private String configKey;

}