package com.buer.flow.biz.service.impl;

import com.buer.flow.api.entity.ActExtModelVariable;
import com.buer.flow.api.query.ModelVariableQuery;
import com.buer.flow.biz.mapper.ActExtModelVariableMapper;
import com.buer.flow.biz.service.ActExtModelVariableService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.buer.flow.api.entity.table.ActExtModelVariableTableDef.ACT_EXT_MODEL_VARIABLE;

/**
 * 模型变量 ServiceImpl
 *
 * @author zoulan
 * @since 2024-05-16
 */
@Service
@RequiredArgsConstructor
public class ActExtModelVariableServiceImpl extends ServiceImpl<ActExtModelVariableMapper, ActExtModelVariable> implements ActExtModelVariableService {

    /**
     * 通过id查询模型变量
     *
     * @param id id
     * @return ActExtModelVariable
     */
    @Override
    public ActExtModelVariable getModelVariableById(Long id) {
        return getQueryChain().and(ACT_EXT_MODEL_VARIABLE.ID.eq(id))
            .one();
    }

    /**
     * 新增模型变量
     *
     * @param modelVariable 模型变量信息
     * @return boolean
     */
    @Override
    public boolean addModelVariable(ActExtModelVariable modelVariable) {
        return save(modelVariable);
    }

    /**
     * 通过id修改模型变量
     *
     * @param modelVariable 模型变量信息
     * @return boolean
     */
    @Override
    public boolean updateModelVariable(ActExtModelVariable modelVariable) {
        return updateById(modelVariable);
    }

    /**
     * 通过id删除模型变量
     *
     * @param id id
     * @return boolean
     */
    @Override
    public boolean removeModelVariableByIds(List<Long> id) {
        return removeByIds(id);
    }

    /**
     * 列表查询模型变量
     *
     * @param entity 查询对象
     * @return 列表
     */
    @Override
    public List<ActExtModelVariable> listModelVariable(ModelVariableQuery entity) {
        return getQueryChainByQuery(entity).listAs(ActExtModelVariable.class);
    }

    /**
     * 分页查询模型变量
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Override
    public Page<ActExtModelVariable> pageModelVariable(Page<ActExtModelVariable> page, ModelVariableQuery entity) {
        return getQueryChainByQuery(entity).pageAs(page, ActExtModelVariable.class);
    }

    /**
     * 获取QueryChain对象
     */
    QueryChain<ActExtModelVariable> getQueryChain() {
        return queryChain().select(ACT_EXT_MODEL_VARIABLE.ALL_COLUMNS);
    }

    /**
     * 通过delegateQuery获取QueryChain对象
     */
    private QueryChain<ActExtModelVariable> getQueryChainByQuery(ModelVariableQuery entity) {
        return getQueryChain()
            .and(ACT_EXT_MODEL_VARIABLE.ID.in(entity.getIdList()))
            .orderBy(ACT_EXT_MODEL_VARIABLE.CREATE_TIME.desc());
    }

}