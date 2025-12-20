package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 岗位 Query
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Schema(description = "岗位信息Query")
public class PostQuery implements Serializable {

    /**
     * 岗位id列表
     */
    @Schema(description = "岗位id列表")
    private String postIds;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    private String name;

}