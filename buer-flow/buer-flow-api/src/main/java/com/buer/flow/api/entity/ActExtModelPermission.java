package com.buer.flow.api.entity;

import com.buer.common.core.entity.SuperEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 模型权限
 *
 * @author zoulan
 * @since 2024-05-16
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "模型权限")
@Table("act_ext_model_permission")
public class ActExtModelPermission extends SuperEntity<ActExtModelPermission> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @Schema(description = "id")
    private Long id;

    /**
     * 模型id
     */
    @Schema(description = "模型id")
    private String modelId;

    /**
     * 权限标识
     */
    @Schema(description = "权限编码")
    private String code;

    /**
     * 权限名称
     */
    @Schema(description = "权限名称")
    private String name;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String depiction;
}