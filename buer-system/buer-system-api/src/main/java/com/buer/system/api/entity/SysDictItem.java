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
 * 字典项 Entity
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "字典项")
@Table("sys_dict_item")
public class SysDictItem extends SuperEntity<SysDictItem> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @Schema(description = "id")
    private Long id;

    /**
     * 字典标识
     */
    @Schema(description = "字典标识")
    private String dictKey;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 值
     */
    @Schema(description = "值")

    private String value;
    /**
     * 主题
     */
    @Schema(description = "主题")
    private String theme;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Double sort;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记")
    private String delFlag;

}