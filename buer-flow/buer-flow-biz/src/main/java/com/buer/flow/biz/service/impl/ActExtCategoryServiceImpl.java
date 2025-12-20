package com.buer.flow.biz.service.impl;

import com.buer.flow.api.entity.ActExtCategory;
import com.buer.flow.api.query.ModelCategoryQuery;
import com.buer.flow.biz.mapper.ActExtCategoryMapper;
import com.buer.flow.biz.service.ActExtCategoryService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.buer.flow.api.entity.table.ActExtCategoryTableDef.ACT_EXT_CATEGORY;


/**
 * 流程类型 ServiceImpl
 *
 * @author zoulan
 * @since 2024-05-16
 */
@Service
@RequiredArgsConstructor
public class ActExtCategoryServiceImpl extends ServiceImpl<ActExtCategoryMapper, ActExtCategory> implements ActExtCategoryService {

    /**
     * 通过id查询流程类型
     *
     * @param id id
     * @return ActExtCategory
     */
    @Override
    public ActExtCategory getModelCategoryById(Long id) {
        return queryChain().and(ACT_EXT_CATEGORY.ID.eq(id))
            .one();
    }

    /**
     * 新增流程类型
     *
     * @param modelCategory 流程类型信息
     * @return boolean
     */
    @Override
    public boolean addModelCategory(ActExtCategory modelCategory) {
        return save(modelCategory);
    }

    /**
     * 通过id修改流程类型
     *
     * @param modelCategory 流程类型信息
     * @return boolean
     */
    @Override
    public boolean updateModelCategory(ActExtCategory modelCategory) {
        return updateById(modelCategory);
    }

    /**
     * 通过id删除流程类型
     *
     * @param modelCategoryIds modelCategoryIds
     * @return boolean
     */
    @Override
    @Transactional
    public boolean removeModelCategoryByIds(List<Long> modelCategoryIds) {
        return removeByIds(modelCategoryIds);
    }

    /**
     * 列表查询流程类型
     *
     * @param modelCategoryQuery 查询对象
     * @return 列表
     */
    @Override
    public List<ActExtCategory> listModelCategory(ModelCategoryQuery modelCategoryQuery) {
        return queryChain().list();
    }

    /**
     * 分页查询流程类型
     *
     * @param page               分页对象
     * @param modelCategoryQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<ActExtCategory> pageModelCategory(Page<ActExtCategory> page, ModelCategoryQuery modelCategoryQuery) {
        return queryChain().page(page);
    }
}