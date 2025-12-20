package com.buer.flow.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 我的实例 VO
 *
 * @author zoulan
 * @since 2023-05-27
 */
@Data
@Accessors(chain = true)
@Schema(description = "我的实例VO")
public class ProcinstForMyQuery implements Serializable {

    /**
     * id列表
     */
    @Schema(description = "id列表")
    private List<Long> ids;

    /**
     * 实例名称
     */
    @Schema(description = "实例名称")
    private String procinstName;

    /**
     * 模型key
     */
    @Schema(description = "key")
    private String key;

    /**
     * 流水号
     */
    @Schema(description = "流水号")
    private String businessKey;
}
