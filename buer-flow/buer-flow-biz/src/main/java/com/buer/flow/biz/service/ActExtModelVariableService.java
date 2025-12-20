package com.buer.flow.biz.service;

import com.buer.flow.api.entity.ActExtModelVariable;
import com.buer.flow.api.query.ModelVariableQuery;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 模型变量 Service
 *
 * @author zoulan
 * @since 2024-05-16
 */
public interface ActExtModelVariableService extends IService<ActExtModelVariable> {

    /**
     * 通过id查询模型变量
     *
     * @param id id
     * @return 模型变量信息
     */
    ActExtModelVariable getModelVariableById(Long id);

    /**
     * 新增模型变量
     *
     * @param modelVariable 模型变量信息
     * @return boolean
     */
    boolean addModelVariable(ActExtModelVariable modelVariable);

    /**
     * 通过id修改模型变量
     *
     * @param modelVariable 模型变量信息
     * @return boolean
     */
    boolean updateModelVariable(ActExtModelVariable modelVariable);

    /**
     * 通过id删除模型变量
     *
     * @param modelVariableIds modelVariableIds
     * @return boolean
     */
    boolean removeModelVariableByIds(List<Long> modelVariableIds);

    /**
     * 列表查询模型变量
     *
     * @param modelVariableQuery 查询对象
     * @return 列表
     */
    List<ActExtModelVariable> listModelVariable(ModelVariableQuery modelVariableQuery);

    /**
     * 分页查询模型变量
     *
     * @param page               分页对象
     * @param modelVariableQuery 查询对象
     * @return 分页对象
     */
    Page<ActExtModelVariable> pageModelVariable(Page<ActExtModelVariable> page, ModelVariableQuery modelVariableQuery);

}