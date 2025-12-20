package com.buer.system.api.entity;

import com.buer.common.core.entity.SuperEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 角色 Entity
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色")
@Table("sys_role")
public class SysRole extends SuperEntity<SysRole> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色Id
     */
    @Id
    @Schema(description = "角色id")
    private Long id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 50, message = "角色名称长度不能超过50个字符")
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
    @Size(max = 500, message = "描述不能超过50个字符")
    @Schema(description = "描述")
    private String depiction;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Double sort;

    /**
     * 0-正常，1-删除
     */
    @Schema(description = "0-正常，1-删除")
    private String delFlag;

    /**
     * 租户id
     */
    @Schema(description = "租户id")
    private String tenantId;

}