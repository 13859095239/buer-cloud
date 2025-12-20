package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典 Query
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Data
@Schema(description = "字典Query")
public class DictQuery implements Serializable {

    /**
     * 字典id列表
     */
    @Schema(description = "字典id列表")
    private String dictIds;

    /**
     * 字典标识
     */
    @Schema(description = "字典标识")
    private String dictKey;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String name;

}