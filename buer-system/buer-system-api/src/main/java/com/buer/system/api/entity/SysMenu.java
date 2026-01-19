package com.buer.system.api.entity;

import com.buer.common.core.entity.SuperEntity;
import com.buer.system.api.enums.MenuTypeEnum;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 菜单 Entity
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "菜单")
@Table("sys_menu")
public class SysMenu extends SuperEntity<SysMenu> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
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
     * 菜单类型,0目录，1菜单，2权限
     */
    @Schema(description = "菜单类型,0目录，1菜单，2权限")
    private MenuTypeEnum menuType;

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


    /**
     * 0-正常，1-删除
     */
    @Schema(description = "0正常，1删除")
    private String delFlag;

}