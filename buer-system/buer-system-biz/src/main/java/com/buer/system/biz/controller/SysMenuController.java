package com.buer.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.system.api.entity.SysMenu;
import com.buer.system.api.query.MenuQuery;
import com.buer.system.api.query.MenuTreeQuery;
import com.buer.system.api.vo.MenuVO;
import com.buer.system.biz.service.SysMenuService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单 Controller
 *
 * @author zoulan
 * @since 2023-05-06
 */
@RestController
@RequestMapping("menu")
@RequiredArgsConstructor
@Tag(name = "菜单")
public class SysMenuController {

    private final SysMenuService service;

    /**
     * 通过id查询菜单
     *
     * @param id id
     * @return MenuVO
     */
    @Operation(summary = "通过id查询菜单")
    @GetMapping(value = "/{id}")
    public R<MenuVO> getMenuById(@PathVariable Long id) {
        return R.ok(service.getMenuById(id));
    }

    /**
     * 新增菜单
     *
     * @param sysMenu SysMenu对象
     * @return Boolean
     */
    @Operation(summary = "新增菜单")
    @PostMapping
    @SaCheckPermission("menu-add")
    public R<Boolean> addMenu(@RequestBody SysMenu sysMenu) {
        return R.ok(service.addMenu(sysMenu));
    }

    /**
     * 通过id修改菜单
     *
     * @param sysMenu SysMenu对象
     * @return Boolean
     */
    @Operation(summary = "通过id修改菜单")
    @PutMapping
    @SaCheckPermission("menu-edit")
    public R<Boolean> updateMenu(@RequestBody SysMenu sysMenu) {
        return R.ok(service.updateMenu(sysMenu));
    }

    /**
     * 通过id删除菜单
     *
     * @param menuIds menuIds
     * @return Boolean
     */
    @SysLog("删除菜单")
    @Operation(summary = "通过id删除菜单")
    @DeleteMapping
    @SaCheckPermission
    public R<Boolean> removeMenuByIds(@RequestBody List<Long> menuIds) {
        return R.ok(service.removeMenuByIds(menuIds));
    }

    /**
     * 列表查询菜单
     *
     * @param entity 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询菜单")
    @GetMapping(value = "/list")
    public R<List<MenuVO>> listMenu(MenuQuery entity) {
        return R.ok(service.listMenu(entity));
    }

    /**
     * 分页查询菜单
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询菜单")
    @GetMapping(value = "/page")
    public R<Page<MenuVO>> pageMenu(Page<MenuVO> page, MenuQuery entity) {
        return R.ok(service.pageMenu(page, entity));
    }

    /**
     * 查询树数据
     *
     * @param menuTreeQuery 查询对象
     * @return Tree
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public R<List<Tree<Long>>> menuTree(MenuTreeQuery menuTreeQuery) {
        return R.ok(service.treeMenu(menuTreeQuery));
    }

    /**
     * 更新菜单树节点排序
     *
     * @param menuIds 需要更新菜单ids排序
     */
    @Operation(summary = "更新菜单树节点排序")
    @PutMapping("/updateMenuTreeSort")
    public R updateMenuTreeSort(@RequestBody List<@NotNull Long> menuIds) {
        service.updateMenuTreeSort(menuIds);
        return R.ok();
    }
}