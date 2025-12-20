package com.buer.flow.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 抄送VO
 *
 * @author zoulan
 * @since 2024-06-13
 */
@Data
@Schema(description = "抄送")
public class CcVO implements Serializable {

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 流程实例id
     */
    @Schema(description = "流程实例id")
    private String procInstId;

    /**
     * 抄送来源
     */
    @Schema(description = "抄送来源")
    private String userId;

    /**
     * 抄送目标
     */
    @Schema(description = "抄送目标")
    private String toUserId;

    /**
     * 抄送时间
     */
    @Schema(description = "抄送时间")
    private LocalDateTime beginTime;

    /**
     * 阅办时间
     */
    @Schema(description = "阅办时间")
    private LocalDateTime endTime;

    /**
     * 阅办意见
     */
    @Schema(description = "阅办意见")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime crDate;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime upDate;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private String upUser;

    /**
     * 更新人id
     */
    @Schema(description = "更新人id")
    private String upUid;

}