package com.buer.system.api.entity;

import com.buer.common.core.entity.SuperEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 日志 Entity
 *
 * @author zoulan
 * @since 2023-05-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "日志")
@Table("sys_log")
public class SysLog extends SuperEntity<SysLog> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Id
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
     * 客户端id
     */
    @Schema(description = "客户端id")
    private String clientId;

    /**
     * 服务ID
     */
    @Schema(description = "服务名称")
    private String serviceName;

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
     * 操作提交的数据
     */
    @Schema(description = "操作提交的数据")
    private String params;

    /**
     * 执行时间
     */
    @Schema(description = "执行时间")
    private Long time;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记")
    private String delFlag;

    /**
     * 异常信息
     */
    @Schema(description = "异常信息")
    private String exception;

}