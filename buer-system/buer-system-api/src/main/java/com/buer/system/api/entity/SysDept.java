package com.buer.system.api.entity;

import com.buer.common.core.entity.SuperEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 部门 Entity
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "部门")
@Table("sys_dept")
public class SysDept extends SuperEntity<SysConfig> {

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
    @Schema(description = "部门名称")
    private String name;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Double sort;

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

    /**
     * 租户id
     */
    @Schema(description = "租户id")
    private String tenantId;

    /**
     * 0-正常，1-删除
     */
    @Schema(description = "0-正常，1-删除")
    private String delFlag;

}