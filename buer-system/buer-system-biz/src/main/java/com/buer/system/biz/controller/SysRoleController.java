package com.buer.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.StrUtil;
import com.buer.system.api.constants.MenuTypeEnum;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.system.api.dto.AddRoleUserDTO;
import com.buer.system.api.entity.SysMenu;
import com.buer.system.api.entity.SysRole;
import com.buer.system.api.query.RoleMenuQuery;
import com.buer.system.api.query.RoleQuery;
import com.buer.system.api.query.RoleUserQuery;
import com.buer.system.api.vo.RoleVO;
import com.buer.system.api.vo.UserVO;
import com.buer.system.biz.service.*;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色 Controller
 *
 * @author zoulan
 * @since 2023-05-06
 */
@RestController
@RequestMapping("role")
@RequiredArgsConstructor
@Tag(name = "角色")
public class SysRoleController {

    private final SysRoleService service;
    private final SysMenuService sysMenuService;
    private final SysRoleMenuService sysRoleMenuService;
    private final SysRoleUserService sysRoleUserService;
    private final SysUserService sysUserService;

    /**
     * 通过id查询角色
     *
     * @param id id
     * @return RoleVO
     */
    @Operation(summary = "通过id查询角色")
    @GetMapping(value = "/{id}")
    public R<RoleVO> getRoleById(@PathVariable Long id) {
        return R.ok(service.getRoleById(id));
    }

    /**
     * 新增角色
     *
     * @param entity SysRole对象
     * @return Boolean
     */
    @SysLog("新增角色")
    @Operation(summary = "新增角色")
    @PostMapping
    @SaCheckPermission("role-add")
    public R<Boolean> addRole(@RequestBody SysRole entity) {
        return R.ok(service.addRole(entity));
    }

    /**
     * 通过id修改角色
     *
     * @param entity SysRole对象
     * @return Boolean
     */
    @SysLog(value = "编辑角色")
    @Operation(summary = "通过id修改角色")
    @PutMapping
    @SaCheckPermission("role-edit")
    public R<Boolean> updateRole(@RequestBody SysRole entity) {
        return R.ok(service.updateRole(entity));
    }

    /**
     * 通过id删除角色
     *
     * @param roleIds roleIds
     * @return Boolean
     */
    @SysLog("删除角色")
    @Operation(summary = "通过id删除角色")
    @DeleteMapping
    @SaCheckPermission("role-delete")
    public R<Boolean> removeRoleByIds(@RequestBody List<Long> roleIds) {
        return R.ok(service.removeRoleByIds(roleIds));
    }

    /**
     * 列表查询角色
     *
     * @param entity 查询对象角色
     * @return 列表
     */
    @SysLog("列表查询角色")
    @Operation(summary = "列表查询角色")
    @GetMapping(value = "/list")
    public R<List<RoleVO>> listRole(RoleQuery entity) {
        return R.ok(service.listRole(entity));
    }

    /**
     * 分页查询角色
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @SysLog("分页查询角色")
    @Operation(summary = "分页查询角色")
    @GetMapping(value = "/page")
    public R<Page<RoleVO>> pageRole(Page<RoleVO> page, RoleQuery entity) {
        return R.ok(service.pageRole(page, entity));
    }

    /**
     * 查询角色下的所有菜单+权限
     *
     * @param roleId roleId
     * @return String
     */
    @Operation(summary = "查询角色下的所有菜单+权限")
    @GetMapping(value = "/menu/list/{roleId}")
    public R<String> getMenuIdsByRoleId(@PathVariable Long roleId) {
        String menuIds = sysMenuService.getMenuListByRole(Collections.singletonList(roleId),
                Arrays.asList(MenuTypeEnum.MENU, MenuTypeEnum.PERMISSION, MenuTypeEnum.PATH))
            .stream()
            .map(item -> item.getId()
                .toString())
            .collect(Collectors.joining(StrUtil.COMMA));
        return R.ok(menuIds);
    }

    /**
     * 查询角色下的所有permission
     *
     * @param roleId roleId
     * @return String
     */
    @Operation(summary = "查询角色下的所有permission")
    @GetMapping(value = "/permission/list/{roleId}")
    public R<List<String>> getPermissionsByRoleId(@PathVariable("roleId") Long roleId) {
        List<String> permissions = sysMenuService.getMenuListByRole(Collections.singletonList(roleId),
                Arrays.asList(MenuTypeEnum.MENU, MenuTypeEnum.PERMISSION, MenuTypeEnum.PATH))
            .stream()
            .map(SysMenu::getPermission)
            .collect(Collectors.toList());
        return R.ok(permissions);
    }

    /**
     * 查询角色下的权限
     *
     * @param entity RoleMenuQuery
     * @return String
     */
    @Operation(summary = "查询角色下的权限")
    @GetMapping(value = "/menu/list")
    public R<List<Long>> getMenuIdList(RoleMenuQuery entity) {
        return null;
        //        List<Long> ids = sysMenuService.getMenuList(entity)
        //                .stream()
        //                .map(RoleMenuVO::getMenuId)
        //                .collect(Collectors.toList());
        //        return R.ok(ids);
    }

    /**
     * 保存角色权限列表
     *
     * @param roleId  角色id
     * @param menuIds 菜单ids
     * @return Boolean
     */
    @SysLog("保存角色权限列表")
    @Operation(summary = "保存角色权限列表")
    @PutMapping(value = "/menu")
    public R saveRoleMenus(Long roleId, String menuIds) {
        return R.ok(sysRoleMenuService.saveRoleMenus(roleId, menuIds));
    }

    /**
     * 新增角色下的用户
     *
     * @param addRoleUserDTO AddRoleUserDTO对象
     * @return R<Boolean>
     */
    @SysLog("新增角色下的用户")
    @Operation(summary = "新增角色下的用户")
    @SaCheckPermission("role-add")
    @PostMapping(value = "/user")
    public R<Boolean> addRoleUser(@RequestBody AddRoleUserDTO addRoleUserDTO) {
        sysRoleUserService.addRoleUser(addRoleUserDTO);
        return R.ok();
    }

    /**
     * 获取角色下的用户列表
     * 获取角色下未添加的用户列表
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 用户的分页对象
     */
    @GetMapping(value = "/user/page")
    public R<Page<UserVO>> pageUserByRole(Page<UserVO> page, RoleUserQuery entity) {
        return R.ok(sysUserService.pageUserByRole(page, entity));
    }

    /**
     * 通过id删除角色下的用户
     *
     * @param roleId  角色id
     * @param userIds 要删除的用户id列表
     * @return Boolean
     */
    @SysLog("删除角色")
    @Operation(summary = "通过id删除角色下的用户")
    @DeleteMapping(value = "/user")
    @SaCheckPermission("role-delete")
    public R<Boolean> deleteRoleUser(@RequestParam Long roleId, @RequestParam String userIds) {
        return R.ok(sysRoleUserService.deleteRoleUser(roleId, userIds));
    }

    /**
     * 查询某个用户下的角色ids
     *
     * @param userId 用户id
     * @return 角色ids
     */
    @GetMapping(value = "/user/{userId}/getRoleIds")
    public R<List<Long>> getRoleIdsByUserId(@PathVariable("userId") Long userId) {
        return R.ok(sysRoleUserService.getRoleIdsByUserId(userId));
    }

}