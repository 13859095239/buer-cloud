package com.buer.flow.api.entity;

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
 * 流程委托
 *
 * @author zoulan
 * @since 2024-06-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "流程委托")
@Table("act_ext_delegate")
public class ActExtDelegate extends SuperEntity<ActExtDelegate> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 委托ID
     */
    @Id
    @Schema(description = "委托ID")
    private Long id;

    /**
     * 被委托人
     */
    @Schema(description = "被委托人")
    private Long userId;

    /**
     * 委托人
     */
    @Schema(description = "委托人")
    private Long toUserId;

    /**
     * 委托开始时间
     */
    @Schema(description = "委托开始时间")
    private LocalDateTime beginTime;

    /**
     * 委托结束时间
     */
    @Schema(description = "委托结束时间")
    private LocalDateTime endTime;

    /**
     * 委托流程（为空是全部流程）
     */
    @Schema(description = "委托流程（为空是全部流程）")
    private String modelId;

    /**
     * 委托原因
     */
    @Schema(description = "委托原因")
    private String depiction;

}