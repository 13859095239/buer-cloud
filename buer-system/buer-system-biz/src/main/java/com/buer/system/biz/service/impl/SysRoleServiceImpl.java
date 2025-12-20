package com.buer.system.biz.service.impl;

import cn.hutool.core.lang.Assert;
import com.buer.common.core.util.StringUtils;
import com.buer.system.api.entity.SysRole;
import com.buer.system.api.entity.SysRoleMenu;
import com.buer.system.api.entity.SysRoleUser;
import com.buer.system.api.query.RoleQuery;
import com.buer.system.api.vo.RoleVO;
import com.buer.system.biz.mapper.SysRoleMapper;
import com.buer.system.biz.service.SysRoleMenuService;
import com.buer.system.biz.service.SysRoleService;
import com.buer.system.biz.service.SysRoleUserService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.buer.system.api.entity.table.SysRoleTableDef.SYS_ROLE;
import static com.buer.system.api.entity.table.SysRoleUserTableDef.SYS_ROLE_USER;

/**
 * 角色 ServiceImpl
 *
 * @author zoulan
 * @since 2021-10-23
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleUserService sysRoleUserService;
    private final SysRoleMenuService sysRoleMenuService;

    /**
     * 通过id查询角色
     *
     * @param id id
     * @return RoleVO
     */
    @Override
    public RoleVO getRoleById(Long id) {
        return getQueryChain().and(SYS_ROLE.ID.eq(id))
            .oneAs(RoleVO.class);
    }

    /**
     * 新增角色
     *
     * @param role SysRole对象
     * @return Boolean
     */
    @Override
    public boolean addRole(SysRole role) {
        checkUnique(role);
        return save(role);
    }

    /**
     * 通过id修改角色
     *
     * @param role SysRole对象
     * @return Boolean
     */
    @Override
    public boolean updateRole(SysRole role) {
        checkUnique(role);
        return updateById(role);
    }

    /**
     * 通过id删除角色
     *
     * @param roleIds roleIds
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean removeRoleByIds(List<Long> roleIds) {
        // List<String> roleIdList = U.getIdListByStringWithEmptyThrow(roleIds, "角色Id");
        // 禁止删除系统角色
        boolean isHasSystemFlag = exists(QueryWrapper.create()
            .in(SysRole::getId, roleIds)
            .eq(SysRole::getSystemFlag, "1"));
        Assert.isFalse(isHasSystemFlag, "删除失败，系统角色无法删除!");
        // 删除角色下的用户
        sysRoleUserService.remove(QueryWrapper.create()
            .in(SysRoleUser::getRoleId, roleIds)
        );
        // 删除角色下的菜单
        sysRoleMenuService.remove(QueryWrapper.create()
            .in(SysRoleMenu::getRoleId, roleIds)
        );
        return removeByIds(roleIds);
    }

    /**
     * 列表查询角色
     *
     * @param roleQuery 查询对象
     * @return 列表
     */
    @Override
    public List<RoleVO> listRole(RoleQuery roleQuery) {
        return getQueryChainByQuery(roleQuery).listAs(RoleVO.class);
    }

    /**
     * 分页查询角色
     *
     * @param page      分页对象
     * @param roleQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<RoleVO> pageRole(Page<RoleVO> page, RoleQuery roleQuery) {
        return getQueryChainByQuery(roleQuery).pageAs(page, RoleVO.class);
    }

    /**
     * 获取QueryChain对象
     */
    QueryChain<SysRole> getQueryChain() {
        return queryChain().select(SYS_ROLE.ALL_COLUMNS);
    }

    /**
     * 通过query获取QueryChain对象
     */
    private QueryChain<SysRole> getQueryChainByQuery(RoleQuery roleQuery) {
        return getQueryChain()
            .and(SYS_ROLE.ID.in(StringUtils.arrayBySplit(roleQuery.getRoleIds())))
            .and(SYS_ROLE.NAME.like(roleQuery.getName()))
            .orderBy(SYS_ROLE.CREATE_TIME.desc());
    }

    /**
     * 通过用户id获取角色列表
     *
     * @param userId userId
     * @return 角色列表
     */
    @Override
    public List<SysRole> getRolesByUserId(Long userId) {
        return queryChain().select(SYS_ROLE.ALL_COLUMNS)
            .from(SYS_ROLE_USER)
            .leftJoin(SYS_ROLE)
            .on(SYS_ROLE_USER.ROLE_ID.eq(SYS_ROLE.ID))
            .and(SYS_ROLE_USER.USER_ID.eq(userId))
            .list();
    }

    /**
     * 判断唯一性
     *
     * @param sysRole sysRole
     */
    void checkUnique(SysRole sysRole) {
        boolean existsName = exists(QueryWrapper.create()
            .eq(SysRole::getName, sysRole.getName())
            .ne(SysRole::getId, sysRole.getId(), sysRole.getId() != null)
        );
        Assert.isFalse(existsName, "保存失败，角色名称重复");
    }
}