package com.buer.system.biz.service.impl;

import com.buer.system.api.dto.AddRoleUserDTO;
import com.buer.system.api.entity.SysRoleUser;
import com.buer.system.biz.mapper.SysRoleUserMapper;
import com.buer.system.biz.service.SysRoleUserService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色用户 ServiceImpl
 *
 * @author zoulan
 * @since 2021-10-23
 */
@Service
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser> implements SysRoleUserService {

    /**
     * 新增角色下的用户列表
     *
     * @param addRoleUserDTO AddRoleUserDTO
     * @return Boolean
     */
    @Override
    @Transactional
    public Boolean addRoleUser(AddRoleUserDTO addRoleUserDTO) {
        Long roleId = addRoleUserDTO.getRoleId();
        Long[] userIds = addRoleUserDTO.getUserIds();
        List<SysRoleUser> sysRoleUsers = new ArrayList<>();
        for (Long userId : userIds) {
            boolean existsUser = exists(QueryWrapper.create()
                .eq(SysRoleUser::getRoleId, roleId)
                .eq(SysRoleUser::getUserId, userId));
            if (!existsUser) {
                sysRoleUsers.add(new SysRoleUser().setRoleId(roleId)
                    .setUserId(userId));
            }
        }
        if (!sysRoleUsers.isEmpty()) {
            saveBatch(sysRoleUsers);
        }
        return true;
    }


    /**
     * 查询某个用户下的角色ids
     *
     * @param userId 用户id
     * @return 角色ids
     */
    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        return list(QueryWrapper.create()
            .select(SysRoleUser::getUserId)
            .eq(SysRoleUser::getUserId, userId))
            .stream()
            .map(SysRoleUser::getUserId)
            .collect(Collectors.toList());
    }

    /**
     * 删除角色下的用户
     *
     * @param roleId  角色id
     * @param userIds 要删除的用户ids
     * @return Boolean
     */
    @Override
    public Boolean deleteRoleUser(Long roleId, String userIds) {
        return remove(QueryWrapper.create()
            .eq(SysRoleUser::getRoleId, roleId)
            .in(SysRoleUser::getUserId, (Object[]) userIds.split(",")));
    }
}