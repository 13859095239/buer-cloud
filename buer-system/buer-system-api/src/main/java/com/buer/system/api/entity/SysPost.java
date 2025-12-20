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
 * 岗位 Entity
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "岗位信息表")
@Table("sys_post")
public class SysPost extends SuperEntity<SysPost> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 岗位ID
     */
    @Id
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

    /**
     * 是否删除  -1：已删除  0：正常
     */
    @Schema(description = "是否删除  -1：已删除  0：正常")
    private String delFlag;

}