package com.buer.resource.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * OSS对象存储表Query
 *
 * @author zoulan
 * @since 2023-08-27
 */
@Data
@Schema(description = "OSS对象存储表Query")
public class OssQuery implements Serializable {

    /**
     * 文件组ID
     */
    @Schema(description = "文件组ID")
    private String fileGroupId;

}