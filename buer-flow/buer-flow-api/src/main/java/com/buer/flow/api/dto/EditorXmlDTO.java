package com.buer.flow.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 更新模型XML DTO
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Accessors(chain = true)
@Schema(description = "更新模型XML")
public class EditorXmlDTO implements Serializable {

    /**
     * 模型id
     */
    @Schema(description = "模型id")
    private String modelId;

    /**
     * jsonXml
     */
    @Schema(description = "模型Xml")
    private String modelXml;


}
