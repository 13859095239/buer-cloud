package com.buer.flow.biz.service;

import com.buer.flow.api.entity.ActExtModelPermission;
import com.buer.flow.api.query.ModelPermissionQuery;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 模型权限 Service
 *
 * @author zoulan
 * @since 2024-05-16
 */
public interface ActExtModelPermissionService extends IService<ActExtModelPermission> {

    /**
     * 通过id查询模型权限
     *
     * @param id id
     * @return 模型权限信息
     */
    ActExtModelPermission getModelPermissionById(Long id);

    /**
     * 新增模型权限
     *
     * @param modelPermission 模型权限信息
     * @return boolean
     */
    boolean addModelPermission(ActExtModelPermission modelPermission);

    /**
     * 通过id修改模型权限
     *
     * @param modelPermission 模型权限信息
     * @return boolean
     */
    boolean updateModelPermission(ActExtModelPermission modelPermission);

    /**
     * 通过id删除模型权限
     *
     * @param modelPermissionIds id
     * @return boolean
     */
    boolean removeModelPermissionByIds(List<Long> modelPermissionIds);

    /**
     * 列表查询模型权限
     *
     * @param modelPermissionQuery 查询对象
     * @return 列表
     */
    List<ActExtModelPermission> listModelPermission(ModelPermissionQuery modelPermissionQuery);

    /**
     * 分页查询模型权限
     *
     * @param page                 分页对象
     * @param modelPermissionQuery 查询对象
     * @return 分页对象
     */
    Page<ActExtModelPermission> pageModelPermission(Page<ActExtModelPermission> page, ModelPermissionQuery modelPermissionQuery);

}