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
 * 编号规则 Entity
 *
 * @author zoulan
 * @since 2024-06-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "编号规则")
@Table("sys_code_rule")
public class SysCodeRule extends SuperEntity<SysCodeRule> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号规则 id
     */
    @Id
    @Schema(description = "id")
    private Long id;

    /**
     * key
     */
    @Schema(description = "编码")
    private String code;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 编号前缀
     */
    @Schema(description = "编号前缀")
    private String prefix;

    /**
     * 编号日期类型
     */
    @Schema(description = "编号日期类型")
    private String codeRuleDateType;

    /**
     * 编号位数
     */
    @Schema(description = "编号位数")
    private Integer digit;

    /**
     * 流水范例
     */
    @Schema(description = "流水范例")
    private String example;

    /**
     * 当前编号
     */
    @Schema(description = "当前编号")
    private Integer currentNumber;

    /**
     * 当前输出编号
     */
    @Schema(description = "当前输出编号")
    private String currentOutput;

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
     * 0-正常，1-删除
     */
    @Schema(description = "0-正常，1-删除")
    private String delFlag;

    /**
     * 租户id
     */
    @Schema(description = "租户id")
    private Long tenantId;

}