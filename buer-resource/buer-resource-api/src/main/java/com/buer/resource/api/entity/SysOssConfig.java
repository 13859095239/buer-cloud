package com.buer.resource.api.entity;

import com.buer.common.core.entity.SuperEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 对象存储配置表
 *
 * @author zoulan
 * @since 2023-08-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "对象存储配置表")
@Table("sys_oss_config")
public class SysOssConfig extends SuperEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主建
     */
    @Id
    @Schema(description = "主建")
    private Long id;

    /**
     * 配置key
     */
    @Schema(description = "配置key")
    private String configKey;

    /**
     * accessKey
     */
    @Schema(description = "accessKey")
    private String accessKey;

    /**
     * 秘钥
     */
    @Schema(description = "秘钥")
    private String secretKey;

    /**
     * 桶名称
     */
    @Schema(description = "桶名称")
    private String bucketName;

    /**
     * 前缀
     */
    @Schema(description = "前缀")
    private String prefix;

    /**
     * 访问站点
     */
    @Schema(description = "访问站点")
    private String endpoint;

    /**
     * 自定义域名
     */
    @Schema(description = "自定义域名")
    private String domain;

    /**
     * 是否https（Y=是,N=否）
     */
    @Schema(description = "是否https（Y=是,N=否）")
    private String isHttps;

    /**
     * 域
     */
    @Schema(description = "域")
    private String region;

    /**
     * 桶权限类型(0=private 1=public 2=custom)
     */
    @Schema(description = "桶权限类型(0=private 1=public 2=custom)")
    private String accessPolicy;

    /**
     * 是否默认（0=是,1=否）
     */
    @Schema(description = "是否默认（0=是,1=否）")
    private String status;

    /**
     * 扩展字段
     */
    @Schema(description = "扩展字段")
    private String ext1;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}