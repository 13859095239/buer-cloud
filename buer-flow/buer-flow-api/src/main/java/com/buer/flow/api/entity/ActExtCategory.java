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
 * 流程类型
 *
 * @author zoulan
 * @since 2024-05-16
 */
@Data(staticConstructor = "create")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "流程类型")
@Table("act_ext_category")
public class ActExtCategory extends SuperEntity<ActExtCategory> {

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
     * 是否允许删除
     */
    @Schema(description = "是否允许删除")
    private String isAllowDel;

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