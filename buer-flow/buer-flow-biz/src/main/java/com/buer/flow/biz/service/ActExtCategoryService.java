package com.buer.flow.biz.service;

import com.buer.flow.api.entity.ActExtCategory;
import com.buer.flow.api.query.ModelCategoryQuery;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 流程类型 Service
 *
 * @author zoulan
 * @since 2024-05-16
 */
public interface ActExtCategoryService extends IService<ActExtCategory> {

    /**
     * 通过id查询流程类型
     *
     * @param id id
     * @return 流程类型信息
     */
    ActExtCategory getModelCategoryById(Long id);

    /**
     * 新增流程类型
     *
     * @param modelCategory 流程类型信息
     * @return boolean
     */
    boolean addModelCategory(ActExtCategory modelCategory);

    /**
     * 通过id修改流程类型
     *
     * @param modelCategory 流程类型信息
     * @return boolean
     */
    boolean updateModelCategory(ActExtCategory modelCategory);

    /**
     * 通过id删除流程类型
     *
     * @param modelCategoryIds modelCategoryIds
     * @return boolean
     */
    boolean removeModelCategoryByIds(List<Long> modelCategoryIds);

    /**
     * 列表查询流程类型
     *
     * @param modelCategoryQuery 查询对象
     * @return 列表
     */
    List<ActExtCategory> listModelCategory(ModelCategoryQuery modelCategoryQuery);

    /**
     * 分页查询流程类型
     *
     * @param page               分页对象
     * @param modelCategoryQuery 查询对象
     * @return 分页对象
     */
    Page<ActExtCategory> pageModelCategory(Page<ActExtCategory> page, ModelCategoryQuery modelCategoryQuery);

}