package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 编号规则 Query
 *
 * @author zoulan
 * @since 2024-06-07
 */
@Data
@Schema(description = "编号规则 Query")
public class CodeRuleQuery implements Serializable {

    /**
     * 编号规则id列表
     */
    @Schema(description = "编号规则id列表")
    private String codeRuleIds;

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
}