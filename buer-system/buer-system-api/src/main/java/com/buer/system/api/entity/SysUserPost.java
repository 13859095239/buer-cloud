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
 * 用户岗位 Entity
 *
 * @author zoulan
 * @since 2023-08-21
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户岗位")
@Table("sys_user_post")
public class SysUserPost extends SuperEntity<SysUserPost> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 岗位ID
     */
    @Id
    @Schema(description = "岗位ID")
    private Long postId;
}