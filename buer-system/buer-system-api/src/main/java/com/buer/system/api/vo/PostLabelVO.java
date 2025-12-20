package com.buer.system.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 岗位外键 VO
 * <p>
 * 仅包含外键引用回填所需的精简字段
 *
 * @author zoulan
 * @since 2025-09-11
 */
@Data
@Schema(description = "岗位外键 VO")
public class PostLabelVO implements Serializable {

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
}


