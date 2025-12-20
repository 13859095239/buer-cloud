package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 团队 Query
 *
 * @author zoulan
 * @since 2024-05-30
 */
@Data
@Schema(description = "团队 Query")
public class TeamQuery implements Serializable {

    /**
     * 团队id列表
     */
    @Schema(description = "团队id列表")
    private String teamIds;

    /**
     * 团队名称
     */
    @Schema(description = "团队名称")
    private String name;

}