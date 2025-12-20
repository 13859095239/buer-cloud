package com.buer.flow.api.dto;

import com.buer.flow.api.constant.ListenerEventEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * 流程监听事件 DTO
 *
 * @author zoulan
 * @since 2024-06-22
 */
@Data
@Accessors(chain = true)
@Schema(description = "流程监听事件DTO")
public class ListenerDTO implements Serializable {

    /**
     * 监听事件类型
     */
    @Schema(description = "监听事件类型")
    private ListenerEventEnum event;

    /**
     * 事件值
     */
    @Schema(description = "事件值")
    private String value;

    /**
     * 流程实例id
     */
    @Schema(description = "流程实例id")
    private String procInstId;

    /**
     * 流水号
     */
    @Schema(description = "流水号")
    private String businessKey;

    /**
     * 实例名称
     */
    @Schema(description = "实例名称")
    private String procName;

    /**
     * 流程变量
     */
    @Schema(description = "流程变量")
    private Map<String, Object> variables;


}
