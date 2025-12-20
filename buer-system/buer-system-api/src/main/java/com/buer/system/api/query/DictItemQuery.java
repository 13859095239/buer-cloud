package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典项 Query
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Data
@Schema(description = "字典项Query")
public class DictItemQuery implements Serializable {

    /**
     * 字典项id列表
     */
    @Schema(description = "字典项id列表")
    private String dictItemIds;

    /**
     * 字典Key
     */
    @Schema(description = "字典Key")
    private String dictKey;

    /**
     * 字典Key列表
     */
    @Schema(description = "字典Key列表")
    private String dictKeys;

    /**
     * 字典id
     */
    @Schema(description = "字典id")
    private String dictId;
}