package com.buer.system.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 岗位 VO
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Schema(description = "岗位信息表")
public class PostVO implements Serializable {

    /**
     * 岗位ID
     */
    @Schema(description = "岗位ID")
    private Long id;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    private String name;

    /**
     * 是否领导
     */
    @Schema(description = "是否领导")
    private String leader;

    /**
     * 岗位描述
     */
    @Schema(description = "岗位描述")
    private String depiction;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Object sort;

}