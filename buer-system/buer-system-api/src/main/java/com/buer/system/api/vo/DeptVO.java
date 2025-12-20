package com.buer.system.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 部门 VO
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Data
@Accessors(chain = true)
@Schema(description = "部门")
public class DeptVO implements Serializable {

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Object sort;

    /**
     * 父ID
     */
    @Schema(description = "父ID")
    private Long parentId;

    /**
     * 地区
     */
    @Schema(description = "地区")
    private String areaId;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String depiction;

}