package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 团队用户 Query
 *
 * @author zoulan
 * @since 2024-05-30
 */
@Data
@Schema(description = "团队用户 Query")
public class TeamUserQuery implements Serializable {

    /**
     * 团队id
     */
    @Schema(description = "团队id")
    private String teamId;

    /**
     * 是否包含团队Id的用户
     */
    @Schema(description = "是否包含teamId的用户")
    private Boolean equalsTeamId;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private String userId;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String nickname;

    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private Long deptId;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

}