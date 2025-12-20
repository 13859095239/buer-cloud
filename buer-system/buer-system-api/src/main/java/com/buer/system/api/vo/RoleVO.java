package com.buer.system.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色 VO
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Data
@Schema(description = "角色")
public class RoleVO implements Serializable {

    /**
     *
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 是否是系统内置
     */
    @Schema(description = "是否是系统内置")
    private String systemFlag;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String depiction;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Object sort;

    /**
     * 是否允许删除
     */
    @Schema(description = "是否允许删除")
    private String isAllowDel;

}