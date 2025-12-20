package com.buer.flow.api.query;

import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 我的待办任务 VO
 *
 * @author zoulan
 * @since 2023-05-27
 */
@Data
@Accessors(chain = true)
@Schema(description = "我的待办任务VO")
public class TaskForMyQuery implements Serializable {

    /**
     * id列表
     */
    @Schema(description = "id列表")
    private List<Long> ids;

    /**
     * 流程实例id
     */
    @Schema(description = "流程实例id")
    @Column("proc_inst_id_")
    private String procInstId;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    private String taskName;

    /**
     * 模型key
     */
    @Schema(description = "key")
    private String modelKey;

    /**
     * 办理人
     */
    @Schema(description = "流水号")
    private String businessKey;
}
