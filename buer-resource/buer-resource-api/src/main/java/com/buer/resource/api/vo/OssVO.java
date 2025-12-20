package com.buer.resource.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * OSS对象存储表VO
 *
 * @author zoulan
 * @since 2023-08-27
 */
@Data
@Schema(description = "OSS对象存储表")
public class OssVO implements Serializable {

    /**
     * 对象存储主键
     */
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
    private String fileGroupId;

    /**
     * 服务商
     */
    @Schema(description = "服务商")
    private String service;

}