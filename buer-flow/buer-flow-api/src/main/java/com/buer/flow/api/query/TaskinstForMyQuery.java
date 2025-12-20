package com.buer.flow.api.query;

import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 我的历史任务 VO
 *
 * @author zoulan
 * @since 2023-05-27
 */
@Data
@Accessors(chain = true)
@Schema(description = "我的历史任务VO")
public class TaskinstForMyQuery implements Serializable {

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
     * 流水号
     */
    @Schema(description = "流水号")
    private String businessKey;
}
