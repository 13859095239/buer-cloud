package com.buer.auth.api.vo;

import com.mybatisflex.annotation.Id;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * token 令牌 Entity
 *
 * @author zoulan
 * @since 2025-08-09
 */
@Data
@Accessors(chain = true)
@Schema(description = "token 令牌 Entity")
public class TokenEndpointVO implements Serializable {

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