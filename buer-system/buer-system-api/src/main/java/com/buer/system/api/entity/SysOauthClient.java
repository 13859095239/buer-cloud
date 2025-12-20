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
 * oauth终端 Entity
 *
 * @author zoulan
 * @since 2024-09-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "oauth终端")
@Table("sys_oauth_client")
public class SysOauthClient extends SuperEntity<SysOauthClient> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 终端ID
     */
    @Id
    @Schema(description = "终端ID")
    private String id;

    @Schema(description = "客户端ID")
    private String clientId;

    /**
     * 客户端密钥
     */
    @Schema(description = "客户端密钥")
    private String clientSecret;

    /**
     * 域
     */
    @Schema(description = "域")
    private String scopes;

    /**
     * 认证类型
     */
    @Schema(description = "认证类型")
    private String authorizedGrantTypes;

    /**
     * 重定向地址
     */
    @Schema(description = "重定向地址")
    private String webServerRedirectUris;

    /**
     * token 有效期
     */
    @Schema(description = "token 有效期")
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效期
     */
    @Schema(description = "刷新令牌有效期")
    private Integer refreshTokenValidity;

    /**
     * 令牌扩展字段JSON
     */
    @Schema(description = "令牌扩展字段JSON")
    private String additionalInformation;

    /**
     * 是否自动放行
     */
    @Schema(description = "是否自动放行")
    private String autoapprove;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String depiction;

}