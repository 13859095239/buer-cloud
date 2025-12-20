package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 请假流程 demoQuery
 *
 * @author zoulan
 * @since 2024-06-07
 */
@Data
@Schema(description = "请假流程demo Query")
public class LeaveQuery implements Serializable {

    /**
     * 请假流程id列表
     */
    @Schema(description = "请假流程id列表")
    private String leaveIds;

}