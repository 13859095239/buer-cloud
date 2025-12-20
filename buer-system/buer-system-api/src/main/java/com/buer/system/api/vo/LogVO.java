package com.buer.system.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 日志 VO
 *
 * @author zoulan
 * @since 2023-05-11
 */
@Data
@Schema(description = "日志")
public class LogVO implements Serializable {

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 日志类型
     */
    @Schema(description = "日志类型")
    private String type;

    /**
     * 日志标题
     */
    @Schema(description = "日志标题")
    private String title;

    /**
     * 服务ID
     */
    @Schema(description = "服务ID")
    private String serviceId;

    /**
     * 操作IP地址
     */
    @Schema(description = "操作IP地址")
    private String remoteAddr;

    /**
     * 用户代理
     */
    @Schema(description = "用户代理")
    private String userAgent;

    /**
     * 请求URI
     */
    @Schema(description = "请求URI")
    private String requestUri;

    /**
     * 操作方式
     */
    @Schema(description = "操作方式")
    private String method;

    /**
     * 执行时间
     */
    @Schema(description = "执行时间")
    private Long time;

    /**
     * 异常信息
     */
    @Schema(description = "异常信息")
    private String exception;

}