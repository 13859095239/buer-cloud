package ${serviceImplPackage};

<#if isDTO>
import cn.hutool.core.bean.BeanUtil;
</#if>
import util.com.buer.common.core.StringUtils;
import util.com.buer.common.core.U;
<#if isDTO>
import ${dtoImport};
</#if>
import ${entityImport};
import ${queryImport};
<#if isVO>
import ${voImport};
</#if>
import ${mapperImport};
import ${serviceImport};
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ${packageName}.api.entity.table.${entity}TableDef.${entityAllUpper};

/**
 * ${cnTableSubName} ServiceImpl
 *
 * @author ${author}
 * @since ${since}
 */
@Service
@RequiredArgsConstructor
public class ${entity}ServiceImpl extends ServiceImpl<${mapperEntity}, ${entity}> implements ${entity}Service {

    /**
     * 通过id查询${cnTableSubName}
     *
     * @param id id
     * @return ${voEntity}
     */
    @Override
    public ${voEntity} get${enTableSubNameUpper}ById(Long id) {
        return getQueryChain().and(${entityAllUpper}.ID.eq(id))
                .oneAs(${voEntity}.class);
    }

    /**
     * 新增${cnTableSubName}
     *
     * @param ${dtoParam} ${cnTableSubName}对象
     * @return Boolean
     */
    @Override
    public boolean add${enTableSubNameUpper}(${dtoEntity} ${dtoParam}) {
<#if isDTO>
        ${entity} ${entityParam} = new ${entity}();
        BeanUtil.copyProperties(${dtoParam}, ${entityParam});
</#if>
        return save(${entityParam});
    }

    /**
     * 通过id修改${cnTableSubName}
     *
     * @param ${dtoParam} ${cnTableSubName}对象
     * @return Boolean
     */
    @Override
    public boolean update${enTableSubNameUpper}(${dtoEntity} ${dtoParam}) {
<#if isDTO>
        ${entity} ${entityParam} = new ${entity}();
        BeanUtil.copyProperties(${dtoParam}, ${entityParam});
</#if>
        return updateById(${entityParam});
    }

    /**
     * 通过${enTableSubName}Ids删除${cnTableSubName}
     *
     * @param ${enTableSubName}Ids ${enTableSubName}Ids
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean remove${enTableSubNameUpper}ByIds(String ${enTableSubName}Ids) {
        List<String> idList = U.getIdListByStringWithEmptyThrow(${enTableSubName}Ids, "${cnTableSubName}Id");
        return removeByIds(idList);
    }

    /**
     * 列表查询${cnTableSubName}
     *
     * @param ${queryParam} 查询对象
     * @return 列表
     */
    @Override
    public List<${voEntity}> list${enTableSubNameUpper}(${queryEntity} ${queryParam}) {
        return getQueryChainBy${queryEntity}(${queryParam}).listAs(${voEntity}.class);
    }

    /**
     * 分页查询${cnTableSubName}
     *
     * @param page        分页对象
     * @param ${queryParam} 查询对象
     * @return 分页对象
     */
    @Override
    public Page<${voEntity}> page${enTableSubNameUpper}(Page<${voEntity}> page, ${queryEntity} ${queryParam}) {
        return getQueryChainBy${queryEntity}(${queryParam}).pageAs(page, ${voEntity}.class);
    }

    /**
     * 获取QueryChain对象
     */
    private QueryChain<${entity}> getQueryChain() {
        return queryChain().select(${entityAllUpper}.ALL_COLUMNS);
    }

    /**
     * 通过${queryParam}获取QueryChain对象
     */
    private QueryChain<${entity}> getQueryChainBy${queryEntity}(${queryEntity} ${queryParam}) {
        return getQueryChain()
                .and(${entityAllUpper}.ID.in(StringUtils.arrayBySplit(${queryParam}.get${enTableSubNameUpper}Ids())))
                .orderBy(${entityAllUpper}.CREATE_TIME.desc());
    }

}