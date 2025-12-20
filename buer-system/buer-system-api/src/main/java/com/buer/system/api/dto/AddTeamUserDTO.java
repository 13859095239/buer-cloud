package com.buer.system.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 新增团队用户 DTO
 *
 * @author zoulan
 * @since 2024-08-22
 */
@Data
@Schema(description = "新增角色用户DTO")
public class AddTeamUserDTO {

    /**
     * 团队id
     */
    @Schema(description = "团队id")
    private Long teamId;

    /**
     * 用户id列表
     */
    @Schema(description = "用户id列表")
    private Long[] userIds;
}
