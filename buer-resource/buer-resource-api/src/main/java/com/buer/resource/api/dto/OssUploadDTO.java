package com.buer.resource.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * OSS上传DTO
 *
 * @author zoulan
 * @since 2023-08-27
 */
@Data
@Accessors(chain = true)
@Schema(description = "文件上传DTO")
public class OssUploadDTO implements Serializable {

    @Schema(description = "文件")
    MultipartFile file;

}