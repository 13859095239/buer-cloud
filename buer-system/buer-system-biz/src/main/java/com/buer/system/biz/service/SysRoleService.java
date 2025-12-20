package com.buer.system.biz.service;

import com.buer.system.api.entity.SysRole;
import com.buer.system.api.query.RoleQuery;
import com.buer.system.api.vo.RoleVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 角色 Service
 *
 * @author zoulan
 * @since 2023-05-06
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 通过id查询角色
     *
     * @param id id
     * @return 角色信息
     */
    RoleVO getRoleById(Long id);

    /**
     * 新增角色
     *
     * @param role
     * @return Boolean
     */
    boolean addRole(SysRole role);

    /**
     * 通过id修改角色
     *
     * @param role
     * @return Boolean
     */
    boolean updateRole(SysRole role);

    /**
     * 通过id删除角色
     *
     * @param roleIds roleIds
     * @return Boolean
     */
    boolean removeRoleByIds(List<Long> roleIds);

    /**
     * 列表查询角色
     *
     * @param roleQuery 查询对象
     * @return 列表
     */
    List<RoleVO> listRole(RoleQuery roleQuery);

    /**
     * 分页查询角色
     *
     * @param page      分页对象
     * @param roleQuery 查询对象
     * @return 分页对象
     */
    Page<RoleVO> pageRole(Page<RoleVO> page, RoleQuery roleQuery);

    /**
     * 通过用户id获取角色列表
     *
     * @param userId userId
     * @return 角色列表
     */
    List<SysRole> getRolesByUserId(Long userId);

}