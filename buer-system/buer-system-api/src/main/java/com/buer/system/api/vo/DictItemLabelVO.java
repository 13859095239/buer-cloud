package com.buer.system.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户外键 VO
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Schema(description = "用户外键 VO")
public class DictItemLabelVO implements Serializable {

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

}