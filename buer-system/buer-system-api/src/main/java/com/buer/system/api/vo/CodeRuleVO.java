package com.buer.system.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 编号规则 VO
 *
 * @author zoulan
 * @since 2024-06-07
 */
@Data
@Schema(description = "编号规则")
public class CodeRuleVO implements Serializable {

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * code
     */
    @Schema(description = "code")
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
     * 描述或说明
     */
    @Schema(description = "描述或说明")
    private String depiction;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Double sort;

}