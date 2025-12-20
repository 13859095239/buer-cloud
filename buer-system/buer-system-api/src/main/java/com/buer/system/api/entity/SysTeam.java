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
 * 团队 Entity
 *
 * @author zoulan
 * @since 2024-05-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "团队")
@Table("sys_team")
public class SysTeam extends SuperEntity<SysTeam> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @Schema(description = "id")
    private Long id;

    /**
     * 团队名称
     */
    @Schema(description = "团队名称")
    private String name;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String depiction;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Double sort;

    /**
     * 租户id
     */
    @Schema(description = "租户id")
    private Long tenantId;

    /**
     * 0-正常，1-删除
     */
    @Schema(description = "0-正常，1-删除")
    private String delFlag;

}