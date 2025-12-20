package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 日志 Query
 *
 * @author zoulan
 * @since 2023-05-11
 */
@Data
@Schema(description = "日志Query")
public class LogQuery implements Serializable {

    /**
     * 日志id列表
     */
    @Schema(description = "日志id列表")
    private String logIds;

}