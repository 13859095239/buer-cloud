package com.buer.system.api.entity;

import com.buer.common.core.entity.SuperEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 令牌 Entity
 *
 * @author zoulan
 * @since 2024-09-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "令牌")
@Table("sys_oauth_token")
public class SysOauthToken extends SuperEntity<SysOauthToken> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @Schema(description = "id")
    private String id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 客户端
     */
    @Schema(description = "客户端")
    private String clientId;

    /**
     * 令牌
     */
    @Schema(description = "令牌")
    private String token;

    /**
     * 令牌有效期
     */
    @Schema(description = "令牌有效期")
    private LocalDateTime validity;

}