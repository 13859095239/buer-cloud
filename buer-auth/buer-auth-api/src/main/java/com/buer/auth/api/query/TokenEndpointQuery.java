package com.buer.auth.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * Token 令牌 Query
 *
 * @author zoulan
 * @since 2028-08-10
 */
@Data
@Schema(description = "编号规则 Query")
public class TokenEndpointQuery implements Serializable {

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;
}