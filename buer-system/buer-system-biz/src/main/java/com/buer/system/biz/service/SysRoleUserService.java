package com.buer.system.biz.service;

import com.buer.system.api.dto.AddRoleUserDTO;
import com.buer.system.api.entity.SysRoleUser;
import com.buer.system.api.vo.UserExportVO;
import com.buer.system.api.vo.UserVO;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 角色用户 Service
 *
 * @author zoulan
 * @since 2021-10-23
 */
public interface SysRoleUserService extends IService<SysRoleUser> {

    /**
     * 新增角色下的用户列表
     *
     * @param addRoleUserDTO AddRoleUserDTO
     * @return Boolean
     */
    Boolean addRoleUser(AddRoleUserDTO addRoleUserDTO);


    /**
     * 查询某个用户下的角色ids
     *
     * @param userId 用户id
     * @return 角色ids
     */
    List<Long> getRoleIdsByUserId(Long userId);

    /**
     * 删除角色下的用户
     *
     * @param roleId  角色id
     * @param userIds 要删除的用户ids
     * @return Boolean
     */
    Boolean deleteRoleUser(Long roleId, String userIds);

    /**
     * 为传入的用户列表回填角色信息
     *
     * @param users 用户列表
     */
    void fillUsersRoleInfo(List<UserVO> users);

    /**
     * 为传入的用户导出列表回填角色信息
     *
     * @param users 用户导出列表
     */
    void fillUsersRoleInfoForExport(List<UserExportVO> users);
}