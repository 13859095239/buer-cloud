package com.buer.flow.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 委托Query
 *
 * @author zoulan
 * @since 2024-06-13
 */
@Data
@Schema(description = "委托 Query")
public class DelegateQuery implements Serializable {

    /**
     * id列表
     */
    @Schema(description = "id列表")
    private List<Long> idList;

}