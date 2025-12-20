package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色 Query
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Data
@Schema(description = "角色Query")
public class RoleQuery implements Serializable {

    /**
     * 角色id列表
     */
    @Schema(description = "角色id列表")
    private String roleIds;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String name;

}