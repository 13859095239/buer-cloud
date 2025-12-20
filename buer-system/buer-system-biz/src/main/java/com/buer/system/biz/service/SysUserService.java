package com.buer.system.biz.service;

import com.buer.system.api.dto.AddUserDTO;
import com.buer.system.api.dto.UpdateUserDTO;
import com.buer.system.api.dto.UserResetPasswordDTO;
import com.buer.system.api.entity.SysUser;
import com.buer.system.api.query.RoleUserQuery;
import com.buer.system.api.query.TeamUserQuery;
import com.buer.system.api.query.UserQuery;
import com.buer.system.api.vo.UserForLoginVO;
import com.buer.system.api.vo.UserInfoVO;
import com.buer.system.api.vo.UserLabelVO;
import com.buer.system.api.vo.UserVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户 Service
 *
 * @author zoulan
 * @since 2021-10-23
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 通过id查询用户
     *
     * @param id id
     * @return 用户信息
     */
    UserVO getUserById(Long id);

    /**
     * 新增用户
     *
     * @param addUserDTO 新增用户 DTO
     * @return Boolean
     */
    boolean addUser(AddUserDTO addUserDTO);

    /**
     * 通过id修改用户
     *
     * @param userDTO 更新用户 DTO
     * @return Boolean
     */
    boolean updateUser(UpdateUserDTO userDTO);

    /**
     * 通过id删除用户
     *
     * @param userIds userIds
     * @return Boolean
     */
    boolean removeUserByIds(List<Long> userIds);

    /**
     * 列表查询用户
     *
     * @param userQuery 查询对象
     * @return 列表
     */
    List<UserVO> listUser(UserQuery userQuery);

    /**
     * 分页查询用户
     *
     * @param page      分页对象
     * @param userQuery 查询对象
     * @return 分页对象
     */
    Page<UserVO> pageUser(Page<UserVO> page, UserQuery userQuery);

    /**
     * 通过用户名查询用户信息
     * 用于登录
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserForLoginVO getByUserName(String username);

    /**
     * 查询当前登录的用户信息
     * 用于登录后查询
     *
     * @return 用户信息
     */
    UserInfoVO getUserInfo();

    /**
     * 重置用户密码
     *
     * @param userResetPasswordDTO 用户重置密码DTO
     * @return Boolean
     */
    Boolean resetPassword(UserResetPasswordDTO userResetPasswordDTO);

    /**
     * 获取角色下的用户列表
     *
     * @param page          分页对象
     * @param roleUserQuery 查询对象
     * @return 用户的分页对象
     */
    Page<UserVO> pageUserByRole(Page<UserVO> page, RoleUserQuery roleUserQuery);

    /**
     * 获取团队下的用户列表
     *
     * @param page          分页对象
     * @param teamUserQuery 查询对象
     * @return 用户的分页对象
     */
    Page<UserVO> pageUserByTeam(Page<UserVO> page, TeamUserQuery teamUserQuery);

    /**
     * 列表查询，用户外键数据
     *
     * @param userIds 用户id列表
     * @return 列表
     */
    List<UserLabelVO> listUserLabelByIds(List<Long> userIds);

    /**
     * 导出用户数据
     *
     * @param response  HTTP响应对象
     * @param userQuery 查询条件
     */
    void exportUser(HttpServletResponse response, UserQuery userQuery);

    /**
     * 导入用户数据
     *
     * @param file 上传的Excel文件
     * @return 导入结果信息
     */
    String importUser(MultipartFile file);

    /**
     * 下载用户导入模板
     *
     * @param response HTTP响应对象
     */
    void downloadUserTemplate(HttpServletResponse response);

}