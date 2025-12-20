package com.buer.system.biz.service.impl;

import com.buer.system.api.dto.AddTeamUserDTO;
import com.buer.system.api.entity.SysTeamUser;
import com.buer.system.biz.mapper.SysTeamUserMapper;
import com.buer.system.biz.service.SysTeamUserService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 团队用户 ServiceImpl
 *
 * @author zoulan
 * @since 2024-05-30
 */
@Service
@RequiredArgsConstructor
public class SysTeamUserServiceImpl extends ServiceImpl<SysTeamUserMapper, SysTeamUser> implements SysTeamUserService {

    /**
     * 新增团队下的用户
     *
     * @param addTeamUserDTO AddTeamUserDTO
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean addTeamUser(AddTeamUserDTO addTeamUserDTO) {
        Long teamId = addTeamUserDTO.getTeamId();
        Long[] userIds = addTeamUserDTO.getUserIds();
        List<SysTeamUser> sysTeamUsers = new ArrayList<>();
        for (Long userId : userIds) {
            boolean existsUser = exists(QueryWrapper.create()
                .eq(SysTeamUser::getTeamId, teamId)
                .eq(SysTeamUser::getUserId, userId));
            if (!existsUser) {
                sysTeamUsers.add(new SysTeamUser().setTeamId(teamId)
                    .setUserId(userId));
            }
        }
        if (!sysTeamUsers.isEmpty()) {
            saveBatch(sysTeamUsers);
        }
        return true;
    }

    /**
     * 删除团队下的用户
     *
     * @param teamId  团队id
     * @param userIds 要删除的用户ids
     * @return Boolean
     */
    @Override
    public Boolean deleteTeamUser(Long teamId, Long[] userIds) {
        return remove(QueryWrapper.create()
            .eq(SysTeamUser::getTeamId, teamId)
            .in(SysTeamUser::getUserId, (Object[]) userIds));
    }
}