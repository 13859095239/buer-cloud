package com.buer.resource.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * OSS更新DTO
 *
 * @author zoulan
 * @since 2023-08-27
 */
@Data
@Accessors(chain = true)
@Schema(description = "文件上传DTO")
public class OssFileGroupDTO implements Serializable {

    /**
     * 文件组ID
     */
    @Schema(description = "文件组ID")
    private Long fileGroupId;

    /**
     * ossIDs
     */
    @Schema(description = "ossIDs")
    private String ids;

}