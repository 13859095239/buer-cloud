package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单 Query
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Data
@Schema(description = "菜单Query")
public class MenuQuery implements Serializable {

    /**
     * 菜单id列表
     */
    @Schema(description = "菜单id列表")
    private String menuIds;

}