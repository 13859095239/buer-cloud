package com.buer.common.core.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 导入结果 VO
 *
 * @author zoulan
 * @since 2025-01-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "导入结果")
public class ImportResultVO implements Serializable {

    /**
     * 总数量
     */
    @Schema(description = "总数量")
    private Integer total;

    /**
     * 成功数量
     */
    @Schema(description = "成功数量")
    private Integer success;

    /**
     * 失败数量
     */
    @Schema(description = "失败数量")
    private Integer failed;

    /**
     * 错误信息列表
     */
    @Schema(description = "错误信息列表")
    private List<ImportErrorVO> errors;

    /**
     * 导入错误信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "导入错误信息")
    public static class ImportErrorVO implements Serializable {

        /**
         * 行号
         */
        @Schema(description = "行号")
        private Integer row;

        /**
         * 错误信息
         */
        @Schema(description = "错误信息")
        private String message;

    }
}
