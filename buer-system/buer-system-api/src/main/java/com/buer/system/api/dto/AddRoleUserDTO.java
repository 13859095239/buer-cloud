package com.buer.system.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 新增角色用户 DTO
 *
 * @author zoulan
 * @since 2024-08-22
 */
@Data
@Schema(description = "新增角色用户DTO")
public class AddRoleUserDTO {

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    private Long roleId;

    /**
     * 用户id列表
     */
    @Schema(description = "用户id列表")
    private Long[] userIds;
}
