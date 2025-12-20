package com.buer.flow.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 历史任务Query
 *
 * @author zoulan
 * @since 2023-05-11
 */
@Data
@Accessors(chain = true)
@Schema(description = "Query")
public class TaskinstQuery extends TaskinstForMyQuery {

    /**
     * 办理人
     */
    @Schema(description = "办理人")
    private String assignee;

}