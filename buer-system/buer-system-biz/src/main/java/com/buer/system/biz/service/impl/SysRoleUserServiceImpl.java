package com.buer.system.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.buer.common.core.constant.CommonConstants;
import com.buer.system.api.dto.AddRoleUserDTO;
import com.buer.system.api.entity.SysRoleUser;
import com.buer.system.api.entity.table.SysRoleTableDef;
import com.buer.system.api.entity.table.SysRoleUserTableDef;
import com.buer.system.api.vo.UserExportVO;
import com.buer.system.api.vo.UserVO;
import com.buer.system.biz.mapper.SysRoleUserMapper;
import com.buer.system.biz.service.SysRoleUserService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
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
            .eq(SysRoleUser::getUserId, userId))
            .stream()
            .map(SysRoleUser::getRoleId)
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

    /**
     * 回填用户角色信息
     *
     * @param users 用户列表
     */
    @Override
    public void fillUsersRoleInfo(List<UserVO> users) {
        fillUsersRoleInfo(users, UserVO::getId, (u, names) -> u.setRoleNames(names));
    }

    /**
     * 回填用户导出角色信息
     *
     * @param users 用户导出列表
     */
    @Override
    public void fillUsersRoleInfoForExport(List<UserExportVO> users) {
        fillUsersRoleInfo(users, u -> StrUtil.isNotBlank(u.getId()) ? Long.valueOf(u.getId()) : null, (u, names) -> u.setRoleNames(names));
    }

    public <T> void fillUsersRoleInfo(List<T> users, Function<T, Long> getIdFunc, BiConsumer<T, String> setRoleNamesFunc) {
        if (CollUtil.isEmpty(users)) {
            return;
        }
        // 获取用户ID列表
        Set<Long> userIds = users.stream().map(getIdFunc).filter(Objects::nonNull).collect(Collectors.toSet());
        if (CollUtil.isEmpty(userIds)) {
            return;
        }
        // 分批查询角色信息
        Map<Long, List<UserIdRoleInfo>> roleInfoMap = CollUtil.split(new ArrayList<>(userIds), CommonConstants.DB_QUERY_BATCH_SIZE).stream()
            .flatMap(batch -> queryChain()
                .select(SysRoleUserTableDef.SYS_ROLE_USER.USER_ID.as("userId"), SysRoleTableDef.SYS_ROLE.NAME.as("roleName"))
                .from(SysRoleUserTableDef.SYS_ROLE_USER)
                .leftJoin(SysRoleTableDef.SYS_ROLE).on(SysRoleUserTableDef.SYS_ROLE_USER.ROLE_ID.eq(SysRoleTableDef.SYS_ROLE.ID))
                .and(SysRoleUserTableDef.SYS_ROLE_USER.USER_ID.in(batch))
                .listAs(UserIdRoleInfo.class).stream())
            .collect(Collectors.groupingBy(UserIdRoleInfo::getUserId));
        users.forEach(user -> {
            Long userId = getIdFunc.apply(user);
            if (userId != null) {
                String roleNames = roleInfoMap.getOrDefault(userId, List.of()).stream()
                    .map(UserIdRoleInfo::getRoleName).filter(Objects::nonNull).collect(Collectors.joining(StrUtil.COMMA));
                setRoleNamesFunc.accept(user, roleNames);
            }
        });
    }

    @Data
    private static class UserIdRoleInfo {
        /**
         * 用户ID
         */
        private Long userId;
        /**
         * 角色名称
         */
        private String roleName;
    }
}