package com.buer.flow.biz.service.impl;

import com.buer.flow.api.entity.ActExtModelPermission;
import com.buer.flow.api.query.ModelPermissionQuery;
import com.buer.flow.biz.mapper.ActExtModelPermissionMapper;
import com.buer.flow.biz.service.ActExtModelPermissionService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.buer.flow.api.entity.table.ActExtModelPermissionTableDef.ACT_EXT_MODEL_PERMISSION;

/**
 * 模型权限 ServiceImpl
 *
 * @author zoulan
 * @since 2024-05-16
 */
@Service
@RequiredArgsConstructor
public class ActExtModelPermissionServiceImpl extends ServiceImpl<ActExtModelPermissionMapper, ActExtModelPermission> implements ActExtModelPermissionService {

    /**
     * 通过id查询模型权限
     *
     * @param id id
     * @return ActExtModelPermission
     */
    @Override
    public ActExtModelPermission getModelPermissionById(Long id) {
        return getQueryChain().and(ACT_EXT_MODEL_PERMISSION.ID.eq(id))
            .one();
    }

    /**
     * 新增模型权限
     *
     * @param modelPermission 模型权限信息
     * @return boolean
     */
    @Override
    public boolean addModelPermission(ActExtModelPermission modelPermission) {
        return save(modelPermission);
    }

    /**
     * 通过id修改模型权限
     *
     * @param modelPermission 模型权限信息
     * @return boolean
     */
    @Override
    public boolean updateModelPermission(ActExtModelPermission modelPermission) {
        return updateById(modelPermission);
    }

    /**
     * 通过id删除模型权限
     *
     * @param modelPermissionIds id
     * @return boolean
     */
    @Override
    public boolean removeModelPermissionByIds(List<Long> modelPermissionIds) {
        return removeByIds(modelPermissionIds);
    }

    /**
     * 列表查询模型权限
     *
     * @param entity 查询对象
     * @return 列表
     */
    @Override
    public List<ActExtModelPermission> listModelPermission(ModelPermissionQuery entity) {
        return getQueryChainByQuery(entity).listAs(ActExtModelPermission.class);
    }

    /**
     * 分页查询模型权限
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Override
    public Page<ActExtModelPermission> pageModelPermission(Page<ActExtModelPermission> page, ModelPermissionQuery entity) {
        return getQueryChainByQuery(entity).pageAs(page, ActExtModelPermission.class);
    }

    /**
     * 获取QueryChain对象
     */
    QueryChain<ActExtModelPermission> getQueryChain() {
        return queryChain().select(ACT_EXT_MODEL_PERMISSION.ALL_COLUMNS);
    }

    /**
     * 通过Query获取QueryChain对象
     */
    private QueryChain<ActExtModelPermission> getQueryChainByQuery(ModelPermissionQuery entity) {
        return getQueryChain()
            .and(ACT_EXT_MODEL_PERMISSION.ID.in(entity.getIdList()))
            .orderBy(ACT_EXT_MODEL_PERMISSION.CREATE_TIME.desc());
    }

}