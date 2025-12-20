package com.buer.system.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 团队 VO
 *
 * @author zoulan
 * @since 2024-05-30
 */
@Data
@Schema(description = "团队")
public class TeamVO implements Serializable {

    /**
     * id
     */
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

}