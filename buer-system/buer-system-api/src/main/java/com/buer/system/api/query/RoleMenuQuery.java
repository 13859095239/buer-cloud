package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 角色菜单 Query
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Accessors(chain = true)

@Schema(description = "岗位信息Query")
public class RoleMenuQuery implements Serializable {

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    private Long roleId;
    /**
     * 菜单类型
     */
    @Schema(description = "菜单类型")
    private List<String> menuTypes;


}