package com.buer.resource.api.entity;

import com.buer.common.core.entity.SuperEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * OSS对象存储表
 *
 * @author zoulan
 * @since 2023-08-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "OSS对象存储表")
@Table("sys_oss")
public class SysOss extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 对象存储主键
     */
    @Id
    @Schema(description = "对象存储主键")
    private Long id;

    /**
     * 文件名
     */
    @Schema(description = "文件名")
    private String fileName;

    /**
     * 桶名
     */
    @Schema(description = "桶名")
    private String bucketName;

    /**
     * 原名
     */
    @Schema(description = "原名")
    private String originalName;

    /**
     * 文件大小
     */
    @Schema(description = "文件大小")
    private Long fileSize;

    /**
     * 文件后缀名
     */
    @Schema(description = "文件后缀名")
    private String fileSuffix;

    /**
     * URL地址
     */
    @Schema(description = "URL地址")
    private String url;

    /**
     * 文件组ID
     */
    @Schema(description = "文件组ID")
    private Long fileGroupId;

    /**
     * 服务商
     */
    @Schema(description = "服务商")
    private String service;

    /**
     * 是否临时,定期清理
     */
    @Schema(description = "是否临时,定期清理")
    private String isTemp;

    /**
     * 排序
     */
    @Schema(description = "是否临时,定期清理")
    private Double sort;


}