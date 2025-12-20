package com.buer.flow.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 流程权限定义Query
 *
 * @author zoulan
 * @since 2024-05-16
 */
@Data
@Schema(description = "流程权限定义 Query")
public class ModelPermissionQuery implements Serializable {

    /**
     * id列表
     */
    @Schema(description = "id列表")
    private List<Long> idList;

}