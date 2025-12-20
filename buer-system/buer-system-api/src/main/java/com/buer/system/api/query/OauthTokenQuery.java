package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 终端信息 Query
 *
 * @author zoulan
 * @since 2024-09-07
 */
@Data
@Schema(description = "终端信息 Query")
public class OauthTokenQuery implements Serializable {

    /**
     * 终端信息id列表
     */
    @Schema(description = "终端信息id列表")
    private String oauthTokenIds;

}