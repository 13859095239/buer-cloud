package com.buer.system.biz.service;

import cn.hutool.core.lang.tree.Tree;
import com.buer.system.api.enums.MenuTypeEnum;
import com.buer.system.api.entity.SysMenu;
import com.buer.system.api.query.MenuQuery;
import com.buer.system.api.query.MenuTreeQuery;
import com.buer.system.api.vo.MenuVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 菜单 Service
 *
 * @author zoulan
 * @since 2023-05-06
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 通过id查询菜单
     *
     * @param id id
     * @return 菜单信息
     */
    MenuVO getMenuById(Long id);

    /**
     * 新增菜单
     *
     * @param sysMenu SysMenu对象
     * @return Boolean
     */
    boolean addMenu(SysMenu sysMenu);

    /**
     * 通过id修改菜单
     *
     * @param sysMenu SysMenu对象
     * @return Boolean
     */
    boolean updateMenu(SysMenu sysMenu);

    /**
     * 通过id删除菜单
     *
     * @param menuIds menuIds
     * @return Boolean
     */
    boolean removeMenuByIds(List<Long> menuIds);

    /**
     * 列表查询菜单
     *
     * @param menuQuery 查询对象
     * @return 列表
     */
    List<MenuVO> listMenu(MenuQuery menuQuery);

    /**
     * 分页查询菜单
     *
     * @param page      分页对象
     * @param menuQuery 查询对象
     * @return 分页对象
     */
    Page<MenuVO> pageMenu(Page<MenuVO> page, MenuQuery menuQuery);

    /**
     * 查询树数据菜单
     *
     * @param menuTreeQuery SysMenuDTO
     * @param menuTreeQuery 查询对象
     * @return Tree
     */
    List<Tree<Long>> treeMenu(MenuTreeQuery menuTreeQuery);

    /**
     * 列表查询菜单,根据角色与菜单类型
     *
     * @param roleIds   角色列表
     * @param menuTypes 菜单类型
     * @return 列表
     */
    List<SysMenu> getMenuListByRole(List<Long> roleIds, List<MenuTypeEnum> menuTypes);

    /**
     * 更新菜单树节点排序
     *
     * @param menuIds 需要更新菜单ids排序
     */
    void updateMenuTreeSort(List<Long> menuIds);
}