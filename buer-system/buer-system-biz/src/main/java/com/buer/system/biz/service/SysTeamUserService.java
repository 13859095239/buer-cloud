package com.buer.system.biz.service;

import com.buer.system.api.dto.AddTeamUserDTO;
import com.buer.system.api.entity.SysTeamUser;
import com.mybatisflex.core.service.IService;

/**
 * 团队用户 Service
 *
 * @author zoulan
 * @since 2024-05-30
 */
public interface SysTeamUserService extends IService<SysTeamUser> {

    /**
     * 新增团队下的用户
     *
     * @param addTeamUserDTO AddTeamUserDTO
     * @return Boolean
     */
    boolean addTeamUser(AddTeamUserDTO addTeamUserDTO);

    /**
     * 删除团队下的用户
     *
     * @param teamId  团队id
     * @param userIds 要删除的用户ids
     * @return Boolean
     */
    Boolean deleteTeamUser(Long teamId, Long[] userIds);
}