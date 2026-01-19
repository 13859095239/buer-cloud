package com.buer.system.api.vo;

import com.buer.common.core.annotation.EnumMapping;
import com.buer.system.api.enums.MenuTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单 VO
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Data
@Schema(description = "菜单")
public class MenuVO implements Serializable {

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
     * 菜单权限标识
     */
    @Schema(description = "菜单权限标识")
    private String permission;

    /**
     * 父菜单ID
     */
    @Schema(description = "父菜单ID")
    private Long parentId;

    /**
     * 路由地址
     */
    @Schema(description = "路由地址")
    private String path;

    /**
     * 组件地址
     */
    @Schema(description = "组件地址")
    private String component;

    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;

    /**
     * 菜单类型,0=目录，1=菜单，2=权限
     */
    @EnumMapping(value = MenuTypeEnum.class)
    @Schema(description = "菜单类型,0=目录，1=菜单，2=权限")
    private String menuType;

    /**
     * 菜单类型描述
     */
    @Schema(description = "菜单类型描述")
    private String menuTypeDesc;

    /**
     * 是否外链
     */
    @Schema(description = "是否外链")
    private String frameFlag;

    /**
     * 外链地址
     */
    @Schema(description = "外链地址")
    private String frameUrl;

    /**
     * 是否可见
     */
    @Schema(description = "是否可见")
    private String visibleFlag;

    /**
     * 是否内嵌
     */
    @Schema(description = "是否内嵌")
    private String embedFlag;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Double sort;


}