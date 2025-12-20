package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 菜单树 Query
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Data
@Accessors(chain = true)
public class MenuTreeQuery implements Serializable {

    /**
     * 父id
     */
    @Schema(description = "父id")
    private Long parentId;
    /**
     * 是否延迟加载
     */
    @Schema(description = "是否延迟加载")
    private Boolean isLazy;

}
