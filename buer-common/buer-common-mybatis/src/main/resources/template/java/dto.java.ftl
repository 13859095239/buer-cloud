package ${dtoPackage};

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
<#if isBigDecimal>
import java.math.BigDecimal;
</#if>
<#if isLocalDateTime>
import java.time.LocalDateTime;
</#if>

/**
 * ${table.comment} Entity
 *
 * @author ${author}
 * @since ${since}
 */
@Data
@Accessors(chain = true)
@Schema(description = "${table.comment} DTO")
public class ${dtoEntity} implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
<#list table.columns as column>
    <#-- 判断是否生成列对象 -->
    <#if utils.generateColumnForVoAndDTO(column.name)>

    /**
     * ${column.comment}
     */
    @Schema(description = "${column.comment!}")
    private ${utils.toJavaType(column.typeName)} ${utils.toCamelCase(column.name)};
    </#if>
</#list>

}