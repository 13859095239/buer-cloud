package com.buer.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.buer.common.core.constant.CacheConstants;
import com.buer.common.core.util.StringUtils;
import com.buer.common.core.util.U;
import com.buer.common.core.vo.ImportResultVO;
import com.buer.common.excel.facade.ExcelUtils;
import com.buer.common.excel.support.ExcelValidatorUtils;
import com.buer.common.redis.util.CacheUtils;
import com.buer.common.security.util.SecurityUtils;
import com.buer.system.api.enums.MenuTypeEnum;
import com.buer.system.api.dto.AddUserDTO;
import com.buer.system.api.dto.UpdateUserDTO;
import com.buer.system.api.dto.UserImportDTO;
import com.buer.system.api.dto.UserResetPasswordDTO;
import com.buer.system.api.entity.SysMenu;
import com.buer.system.api.entity.SysRole;
import com.buer.system.api.entity.SysUser;
import com.buer.system.api.entity.SysUserPost;
import com.buer.system.api.query.RoleUserQuery;
import com.buer.system.api.query.TeamUserQuery;
import com.buer.system.api.query.UserQuery;
import com.buer.system.api.vo.*;
import com.buer.system.biz.mapper.SysUserMapper;
import com.buer.system.biz.mapper.SysUserPostMapper;
import com.buer.system.biz.service.SysMenuService;
import com.buer.system.biz.service.SysRoleService;
import com.buer.system.biz.service.SysRoleUserService;
import com.buer.system.biz.service.SysUserPostService;
import com.buer.system.biz.service.SysUserService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.tenant.TenantManager;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.buer.system.api.entity.table.SysDeptTableDef.SYS_DEPT;
import static com.buer.system.api.entity.table.SysRoleUserTableDef.SYS_ROLE_USER;
import static com.buer.system.api.entity.table.SysTeamUserTableDef.SYS_TEAM_USER;
import static com.buer.system.api.entity.table.SysUserTableDef.SYS_USER;

/**
 * 用户 ServiceImpl
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder(12);
    private final SysMenuService sysMenuService;
    private final SysRoleService sysRoleService;
    private final SysRoleUserService sysRoleUserService;
    private final SysUserPostMapper sysUserPostMapper;
    private final SysUserPostService sysUserPostService;
    private final ExcelUtils excelUtils;
    private final ExcelValidatorUtils excelValidator;

    /**
     * 通过id查询用户
     *
     * @param id id
     * @return UserVO
     */
    @Override
    public UserVO getUserById(Long id) {
        UserVO entity = getQueryChain().and(SYS_USER.ID.eq(id))
            .oneAs(UserVO.class);
        // 回填用户岗位信息（postIds, postNames）
        sysUserPostService.fillUsersPostInfo(Collections.singletonList(entity));
        return entity;
    }

    /**
     * 新增用户
     *
     * @param addUserDTO 新增用户 DTO
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean addUser(AddUserDTO addUserDTO) {
        SysUser model = new SysUser();
        Assert.isTrue(exists(queryChain().and(SYS_USER.USERNAME.eq(addUserDTO.getUsername()))), "用户名不能重复");
        Assert.isTrue(exists(queryChain().and(SYS_USER.PHONE.eq(addUserDTO.getPhone()))), "手机号不能重复");
        // 判断密码不能为空
        Assert.isTrue(!StrUtil.isBlank(addUserDTO.getPassword()), "密码不能为空");
        BeanUtil.copyProperties(addUserDTO, model);
        model.setPassword(ENCODER.encode(model.getPassword()));
        save(model);
        // 保存用户岗位信息
        List<String> postIdList = U.getIdListByStringWithDefault(addUserDTO.getPostIds());
        Opt.ofBlankAble(postIdList)
            .ifPresent(postIds -> postIdList.stream()
                .map(postId -> {
                    SysUserPost userPost = new SysUserPost();
                    userPost.setUserId(model.getId());
                    userPost.setPostId(Long.valueOf(postId));
                    return userPost;
                })
                .forEach(sysUserPostMapper::insert));
        return true;
    }

    /**
     * 通过id修改用户
     *
     * @param userDTO 更新用户 DTO
     * @return Boolean
     */
    @Override
    @Transactional
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDTO.id")
    public boolean updateUser(UpdateUserDTO userDTO) {
        SysUser model = new SysUser();
        BeanUtil.copyProperties(userDTO, model);

        // 删除用户岗位信息
        sysUserPostMapper.deleteByQuery(QueryWrapper.create()
            .eq(SysUserPost::getUserId, userDTO.getId()));
        // 保存用户岗位信息
        List<String> postIdList = U.getIdListByStringWithDefault(userDTO.getPostIds());
        Opt.ofBlankAble(userDTO.getPostIds())
            .ifPresent(postIds -> postIdList.stream()
                .map(postId -> {
                    SysUserPost userPost = new SysUserPost();
                    userPost.setUserId(userDTO.getId());
                    userPost.setPostId(Long.valueOf(postId));
                    return userPost;
                })
                .forEach(sysUserPostMapper::insert));
        return updateById(model);
    }

    /**
     * 通过id删除用户
     *
     * @param userIds userIds
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean removeUserByIds(List<Long> userIds) {
        //List<String> userIdList = U.getIdListByStringWithEmptyThrow(userIds, "用户Id");
        removeByIds(userIds);
        userIds.forEach(id -> CacheUtils.evict(CacheConstants.USER_DETAILS, id));
        return true;
    }

    /**
     * 列表查询用户
     *
     * @param userQuery 查询对象
     * @return 列表
     */
    @Override
    public List<UserVO> listUser(UserQuery userQuery) {
        return getQueryChainByQuery(userQuery).listAs(UserVO.class);
    }

    /**
     * 分页查询用户
     *
     * @param page      分页对象
     * @param userQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<UserVO> pageUser(Page<UserVO> page, UserQuery userQuery) {
        return getQueryChainByQuery(userQuery).pageAs(page, UserVO.class);
    }

    /**
     * 获取QueryChain对象
     */
    QueryChain<SysUser> getQueryChain() {
        return queryChain().select(SYS_USER.ALL_COLUMNS,
                SYS_DEPT.NAME.as("deptName"))
            .leftJoin(SYS_DEPT)
            .on(SYS_USER.DEPT_ID.eq(SYS_DEPT.ID));
    }

    /**
     * 通过userQuery获取QueryChain对象
     */
    private QueryChain<SysUser> getQueryChainByQuery(UserQuery entity) {
        return getQueryChain()
            .and(SYS_USER.ID.in(StringUtils.arrayBySplit(entity.getUserIds())))
            .and(SYS_USER.USERNAME.like(entity.getUsername()))
            .and(SYS_USER.NICKNAME.like(entity.getNickname()))
            .and(SYS_USER.DEPT_ID.eq(entity.getDeptId()))
            .and(SYS_USER.PHONE.eq(entity.getPhone()))
            .orderBy(SYS_USER.ID.asc(), SYS_USER.CREATE_TIME.desc());
    }

    /**
     * 通过用户名查询用户信息
     * 用于登录
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public UserForLoginVO getByUserName(String username) {
        // 忽略租户条件
        TenantManager.ignoreTenantCondition();
        // 登录验证时，忽略租户
        SysUser user = getOne(QueryWrapper.create()
            .eq(SysUser::getUsername, username));
        Assert.notNull(user, "用户不存在");
        UserForLoginVO vo = new UserForLoginVO();
        List<SysRole> roles = sysRoleService.getRolesByUserId(user.getId());
        List<Long> roleIds = roles.stream()
            .map(SysRole::getId)
            .collect(Collectors.toList());
        vo.setRoles(ArrayUtil.toArray(roleIds, Long.class));
        List<SysMenu> sysMenuList;
        if ("1".equals(user.getAdminFlag())) {
            // 管理员获取所有权限
            sysMenuList = sysMenuService.list();
        } else {
            sysMenuList = sysMenuService.getMenuListByRole(roleIds, ListUtil.toList(MenuTypeEnum.PATH, MenuTypeEnum.MENU, MenuTypeEnum.PERMISSION));
        }
        List<String> permissions = sysMenuList.stream()
            .filter(p -> MenuTypeEnum.PERMISSION
                .equals(p.getMenuType()))
            .map(SysMenu::getPermission)
            .collect(Collectors.toList());
        vo.setUser(user)
            .setPermissions(ArrayUtil.toArray(permissions, String.class));
        // 恢复租户条件
        TenantManager.restoreTenantCondition();
        return vo;
    }

    /**
     * 查询当前登录的用户信息
     * 用于登录后查询
     *
     * @return 用户信息
     */
    @Override
    public UserInfoVO getUserInfo() {
        Long userId = SecurityUtils.getUser()
            .getId();
        return getUserInfo(userId);
    }

    private UserInfoVO getUserInfo(Long userId) {
        SysUser user = getOne(QueryWrapper.create()
            .eq(SysUser::getId, userId));
        List<SysRole> roles = sysRoleService.getRolesByUserId(user.getId());
        List<Long> roleIds = roles.stream()
            .map(SysRole::getId)
            .collect(Collectors.toList());
        UserInfoVO vo = new UserInfoVO();
        vo.setRoles(roleIds);
        List<SysMenu> sysMenuList;
        if ("1".equals(user.getAdminFlag())) {
            // 管理员获取所有权限
            sysMenuList = sysMenuService.list();
        } else {
            sysMenuList = sysMenuService.getMenuListByRole(roleIds, ListUtil.toList(MenuTypeEnum.PATH, MenuTypeEnum.MENU, MenuTypeEnum.PERMISSION));
        }

        List<SysMenu> menus = sysMenuList.stream()
            .filter(p -> Arrays.asList(MenuTypeEnum.PATH, MenuTypeEnum.MENU, MenuTypeEnum.PERMISSION)
                .contains(p.getMenuType()))
            .collect(Collectors.toList());
        List<String> permissions = sysMenuList.stream()
            .filter(p -> MenuTypeEnum.PERMISSION
                .equals(p.getMenuType()))
            .map(SysMenu::getPermission)
            .collect(Collectors.toList());
        vo.setUser(user)
            .setMenus(menus)
            .setPermissions(permissions);
        return vo;
    }

    /**
     * 重置用户密码
     *
     * @param userResetPasswordDTO 用户重置密码DTO
     * @return Boolean
     */
    @Override
    public Boolean resetPassword(UserResetPasswordDTO userResetPasswordDTO) {
        return UpdateChain.of(SysUser.class)
            .set(SysUser::getPassword, ENCODER.encode(userResetPasswordDTO.getNewPassword()))
            .eq(SysUser::getId, userResetPasswordDTO.getId())
            .update();
    }

    /**
     * 获取角色下的用户列表
     *
     * @param page          分页对象
     * @param roleUserQuery 查询对象
     * @return 用户的分页对象
     */
    @Override
    public Page<UserVO> pageUserByRole(Page<UserVO> page, RoleUserQuery roleUserQuery) {
        UserQuery userQuery = new UserQuery();
        BeanUtil.copyProperties(roleUserQuery, userQuery);
        QueryWrapper roleUserQueryWrapper = QueryChain.create()
            .from(SYS_ROLE_USER)
            .select(SYS_ROLE_USER.USER_ID)
            .and(SYS_ROLE_USER.ROLE_ID.eq(roleUserQuery.getRoleId()));
        return getQueryChainByQuery(userQuery)
            .and(SYS_USER.ID.in(roleUserQueryWrapper, roleUserQuery.getEqualsRoleId()))
            .and(SYS_USER.ID.notIn(roleUserQueryWrapper, !roleUserQuery.getEqualsRoleId()))
            .pageAs(page, UserVO.class);
    }

    /**
     * 获取团队下的用户列表
     *
     * @param page          分页对象
     * @param teamUserQuery 查询对象
     * @return 用户的分页对象
     */
    @Override
    public Page<UserVO> pageUserByTeam(Page<UserVO> page, TeamUserQuery teamUserQuery) {
        UserQuery userQuery = new UserQuery();
        BeanUtil.copyProperties(teamUserQuery, userQuery);
        // 子查询team表
        QueryWrapper teamQueryWrapper = QueryChain.create()
            .from(SYS_TEAM_USER)
            .select(SYS_TEAM_USER.USER_ID)
            .and(SYS_TEAM_USER.TEAM_ID.eq(teamUserQuery.getTeamId()));
        return getQueryChainByQuery(userQuery)
            .and(SYS_USER.ID.in(teamQueryWrapper, teamUserQuery.getEqualsTeamId()))
            .and(SYS_USER.ID.notIn(teamQueryWrapper, !teamUserQuery.getEqualsTeamId()))
            .pageAs(page, UserVO.class);
    }

    /**
     * 列表查询，用户外键数据
     *
     * @param userIds 用户id列表
     * @return 列表
     */
    @Override
    public List<UserLabelVO> listUserLabelByIds(List<Long> userIds) {
        if (userIds.isEmpty()) {
            return new ArrayList<>();
        }
        return queryChain().select(SYS_USER.ID, SYS_USER.USERNAME, SYS_USER.NICKNAME, SYS_USER.PHONE)
            .and(SYS_USER.ID.in(userIds))
            .listAs(UserLabelVO.class);
    }

    /**
     * 判断唯一性
     *
     * @param sysUser sysUser
     */
    void checkUnique(SysUser sysUser) {
        boolean existsName = exists(QueryWrapper.create()
            .eq(SysUser::getUsername, sysUser.getUsername())
            .ne(SysUser::getId, sysUser.getId(), sysUser.getId() != null)
        );
        Assert.isFalse(existsName, "保存失败，用户名称重复");
        boolean existsPhone = exists(QueryWrapper.create()
            .eq(SysUser::getPhone, sysUser.getPhone())
            .ne(SysUser::getId, sysUser.getId(), sysUser.getId() != null)
        );
        Assert.isFalse(existsPhone, "保存失败，手机号重复");
    }

    /**
     * 导出用户数据
     *
     * @param response  HTTP响应对象
     * @param userQuery 查询条件
     */
    @Override
    public void exportUser(HttpServletResponse response, UserQuery userQuery) {
        List<UserExportVO> exportList = getQueryChainByQuery(userQuery).listAs(UserExportVO.class);
        sysUserPostService.fillUsersPostInfoForExport(exportList);
        sysRoleUserService.fillUsersRoleInfoForExport(exportList);
        excelUtils.exportExcel(response, "用户数据", exportList, UserExportVO.class);
    }

    /**
     * 导入用户数据
     *
     * @param file 上传的Excel文件
     * @return 导入结果信息
     */
    @Override
    @Transactional
    public ImportResultVO importUser(MultipartFile file) {
        try {
            // 1. 验证上传文件
            excelValidator.validateFile(file);

            // 2. 使用Excel工具类读取数据
            List<UserImportDTO> importList = excelUtils.readExcel(file, UserImportDTO.class);
            if (importList.isEmpty()) {
                return new ImportResultVO(0, 0, 0, null);
            }

            // 3. 验证导入数据行数
            if (!excelValidator.isValidImportRows(importList)) {
                throw new IllegalArgumentException("导入数据行数超过限制，请分批导入");
            }
            // 4. 业务数据验证和处理
            int successCount = 0;
            int failCount = 0;
            List<ImportResultVO.ImportErrorVO> errorList = new ArrayList<>();

            for (int i = 0; i < importList.size(); i++) {
                UserImportDTO importDTO = importList.get(i);
                try {
                    SysUser sysUser = new SysUser();
                    BeanUtil.copyProperties(importDTO, sysUser);
                    checkUnique(sysUser);
                    // 保存用户数据
                    //saveImportUser(importDTO);
                    successCount++;

                } catch (Exception e) {
                    failCount++;
                    ImportResultVO.ImportErrorVO error = new ImportResultVO.ImportErrorVO(
                        i + 2, null, e.getMessage(), null
                    );
                    errorList.add(error);

                    log.warn("用户导入失败，行号：{}，数据：{}，错误：{}",
                        i + 2, importDTO.getUsername(), e.getMessage());
                }
            }

            // 5. 返回导入结果
            int total = successCount + failCount;
            ImportResultVO result = new ImportResultVO(
                total,
                successCount,
                failCount,
                errorList.isEmpty() ? null : errorList
            );

            log.info("用户数据导入完成，成功：{}条，失败：{}条", successCount, failCount);
            return result;

        } catch (Exception e) {
            log.error("用户数据导入失败，错误信息：{}", e.getMessage(), e);
            throw new RuntimeException("用户数据导入失败：" + e.getMessage(), e);
        }
    }

}