package com.buer.system.biz.service;

import com.buer.system.api.entity.SysUserPost;
import com.buer.system.api.vo.UserExportVO;
import com.buer.system.api.vo.UserVO;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 用户岗位 Service
 */
public interface SysUserPostService extends IService<SysUserPost> {

    /**
     * 为传入的用户列表回填岗位信息（postIds, postNames）
     *
     * @param users 用户列表
     */
    void fillUsersPostInfo(List<UserVO> users);

    /**
     * 为传入的用户导出列表回填岗位信息（postNames）
     *
     * @param users 用户导出列表
     */
    void fillUsersPostInfoForExport(List<UserExportVO> users);
}


