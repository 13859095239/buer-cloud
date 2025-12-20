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
 * 团队用户 Entity
 *
 * @author zoulan
 * @since 2024-05-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "团队用户")
@Table("sys_team_user")
public class SysTeamUser extends SuperEntity<SysTeamUser> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 团队Id
     */
    @Id
    @Schema(description = "团队Id")
    private Long teamId;

    /**
     * 用户Id
     */
    @Id
    @Schema(description = "用户Id")
    private Long userId;

}