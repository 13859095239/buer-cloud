package ${entityPackage};

import entity.com.buer.common.core.SuperEntity;
<#if isLogicDeleteColumn>
import com.mybatisflex.annotation.Column;
</#if>
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
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
@EqualsAndHashCode(callSuper = true)
@Schema(description = "${table.comment}")
@Table("${table.tableName}")
public class ${entity} extends SuperEntity<${entity}> {

    @Serial
    private static final long serialVersionUID = 1L;
<#list table.columns as column>
    <#-- 判断是否生成列对象，SuperEntity类中的实体不生成 -->
    <#if utils.generateColumn(column.name)>

    /**
     * ${column.comment}
     */
    <#-- Id列 -->
    <#if column.isPk()>
    @Id
    </#if>
    <#-- 逻辑删除列 -->
    <#if column.name == logicDeleteColumnName>
    @Column(isLogicDelete = true)
    </#if>
    @Schema(description = "${column.comment!}")
    private ${utils.toJavaType(column.typeName)} ${utils.toCamelCase(column.name)};
    </#if>
</#list>

}