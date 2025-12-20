package com.buer.system.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典 VO
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Data
@Schema(description = "字典")
public class DictVO implements Serializable {

    /**
     * id
     */
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
     * 描述
     */
    @Schema(description = "描述")
    private String description;

    /**
     * 是否是系统内置
     */
    @Schema(description = "是否是系统内置")
    private String systemFlag;

}