package ${servicePackage};

<#if isDTO>
import ${dtoImport};
</#if>
import ${entityImport};
import ${queryImport};
<#if isVO>
import ${voImport};
</#if>
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * ${cnTableSubName} Service
 *
 * @author ${author}
 * @since ${since}
 */
public interface ${entity}Service extends IService<${entity}> {

    /**
     * 通过id查询${cnTableSubName}
     *
     * @param id id
     * @return ${cnTableSubName}信息
     */
    ${voEntity} get${enTableSubNameUpper}ById(Long id);

    /**
     * 新增${cnTableSubName}
     *
     * @param ${dtoParam} ${cnTableSubName}对象
     * @return Boolean
     */
    boolean add${enTableSubNameUpper}(${dtoEntity} ${dtoParam});

    /**
     * 通过id修改${cnTableSubName}
     *
     * @param ${dtoParam} ${cnTableSubName}对象
     * @return Boolean
     */
    boolean update${enTableSubNameUpper}(${dtoEntity} ${dtoParam});

    /**
     * 通过${enTableSubName}Ids删除${cnTableSubName}
     *
     * @param ${enTableSubName}Ids ${enTableSubName}Ids
     * @return Boolean
     */
    boolean remove${enTableSubNameUpper}ByIds(String ${enTableSubName}Ids);

    /**
     * 列表查询${cnTableSubName}
     *
     * @param ${queryParam} 查询对象
     * @return 列表
     */
    List<${voEntity}> list${enTableSubNameUpper}(${queryEntity} ${queryParam});

    /**
     * 分页查询${cnTableSubName}
     *
     * @param page        分页对象
     * @param ${queryParam} 查询对象
     * @return 分页对象
     */
    Page<${voEntity}> page${enTableSubNameUpper}(Page<${voEntity}> page, ${queryEntity} ${queryParam});

}