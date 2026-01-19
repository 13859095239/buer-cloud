package com.buer.system.biz.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.buer.common.core.exception.CheckException;
import com.buer.common.core.util.EnumDescHandler;
import com.buer.system.api.enums.MenuTypeEnum;
import com.buer.system.api.entity.SysMenu;
import com.buer.system.api.query.MenuQuery;
import com.buer.system.api.query.MenuTreeQuery;
import com.buer.system.api.vo.MenuVO;
import com.buer.system.biz.mapper.SysMenuMapper;
import com.buer.system.biz.service.SysMenuService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.buer.system.api.entity.table.SysMenuTableDef.SYS_MENU;
import static com.buer.system.api.entity.table.SysRoleMenuTableDef.SYS_ROLE_MENU;

/**
 * 菜单 ServiceImpl
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    /**
     * 通过id查询菜单
     *
     * @param id id
     * @return MenuVO
     */
    @Override
    public MenuVO getMenuById(Long id) {
        return queryChain().and(SYS_MENU.ID.eq(id))
            .oneAs(MenuVO.class);
    }

    /**
     * 新增菜单
     *
     * @param sysMenu SysMenu对象
     * @return Boolean
     */
    @Override
    public boolean addMenu(SysMenu sysMenu) {
        prepareMenu(sysMenu);
        if (sysMenu.getParentId() != null && sysMenu.getMenuType()
            .equals(MenuTypeEnum.PATH)) {
            // 添加子目录，父节点必须是目录
            SysMenu parentMenu = getOne(QueryWrapper.create()
                .and(SYS_MENU.ID.eq(sysMenu.getParentId())));
            if (!parentMenu.getMenuType()
                .equals(MenuTypeEnum.PATH)
            ) {
                throw new CheckException("保存失败，添加子目录，父节点必须是目录！");
            }
        }
        return save(sysMenu);
    }

    /**
     * 通过id修改菜单
     *
     * @param sysMenu SysMenu对象
     * @return Boolean
     */
    @Override
    public boolean updateMenu(SysMenu sysMenu) {
        prepareMenu(sysMenu);
        if (!sysMenu.getMenuType()
            .equals(MenuTypeEnum.PATH)) {
            // 修改菜单类型为菜单/权限时,当前节点不能有子节点
            Assert.isFalse(exists(QueryWrapper.create()
                .and(SYS_MENU.PARENT_ID.eq(sysMenu.getId()))), "保存失败，当前节点有子节点，类型必须为目录！");
        }
        return updateById(sysMenu);
    }

    /**
     *
     * 保存前预处理数据
     *
     * @param sysMenu SysMenu对象
     */
    private void prepareMenu(SysMenu sysMenu) {
        switch (sysMenu.getMenuType()) {
            case MenuTypeEnum.MENU:
            case MenuTypeEnum.PERMISSION:
                sysMenu.setEmbedFlag(null)
                    .setFrameFlag(null)
                    .setFrameUrl(null)
                    .setComponent(null);
                break;
            case MenuTypeEnum.PATH:
                sysMenu.setPermission(null);
                break;
        }
        if (MenuTypeEnum.MENU.equals(sysMenu.getMenuType())) {
            sysMenu.setPermission(null);
            if ("1".equals(sysMenu.getFrameFlag())) {
                // 外链
                sysMenu.setComponent(null);
            } else {
                // 内部组件
                sysMenu.setFrameFlag(null);
            }
        }
    }

    /**
     * 通过id删除菜单
     *
     * @param menuIds menuIds
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean removeMenuByIds(List<Long> menuIds) {
        return removeByIds(menuIds);
    }

    /**
     * 列表查询菜单
     *
     * @param menuQuery 查询对象
     * @return 列表
     */
    @Override
    public List<MenuVO> listMenu(MenuQuery menuQuery) {
        List<MenuVO> dataList = queryChain().listAs(MenuVO.class);
        EnumDescHandler.fillEnumDesc(dataList);
        return dataList;
    }

    /**
     * 分页查询菜单
     *
     * @param page      分页对象
     * @param menuQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<MenuVO> pageMenu(Page<MenuVO> page, MenuQuery menuQuery) {
        Page<MenuVO> dataPage = queryChain().pageAs(page, MenuVO.class);
        EnumDescHandler.fillEnumDesc(dataPage);
        return dataPage;
    }

    /**
     * 查询菜单树数据
     *
     * @param menuTreeQuery 查询对象
     * @return Tree
     */
    @Override
    public List<Tree<Long>> treeMenu(MenuTreeQuery menuTreeQuery) {
        List<TreeNode<Long>> nodes = list(QueryWrapper.create()
            .isNull(SysMenu::getParentId, menuTreeQuery.getIsLazy())
            .eq(SysMenu::getParentId, menuTreeQuery.getParentId(), menuTreeQuery.getIsLazy())).stream()
            .map(getNodeFunction())
            .collect(Collectors.toList());
        List<Tree<Long>> tree = TreeUtil.build(nodes, menuTreeQuery.getParentId());
        return ObjectUtil.defaultIfNull(tree, new ArrayList<>());
    }

    /**
     * 列表查询菜单,根据角色与菜单类型
     *
     * @param roleIds 角色列表
     * @param types   菜单类型
     * @return 列表
     */
    @Override
    public List<SysMenu> getMenuListByRole(List<Long> roleIds, List<MenuTypeEnum> types) {
        if (ObjectUtil.isEmpty(roleIds)) {
            // 角色为空的时候，返回空权限，避免获取所有的权限
            return new ArrayList<>();
        }
        return queryChain()
            .select(QueryMethods.distinct(SYS_MENU.ALL_COLUMNS))
            .from(SYS_MENU)
            .leftJoin(SYS_ROLE_MENU)
            .on(SYS_MENU.ID.eq(SYS_ROLE_MENU.MENU_ID))
            .and(SYS_ROLE_MENU.ROLE_ID.in(roleIds))
            .and(SYS_MENU.MENU_TYPE.in(types))
            .list();
    }

    /**
     * 更新菜单树节点排序
     *
     * @param deptIds 需要更新菜单ids排序
     */
    @Override
    public void updateMenuTreeSort(List<Long> deptIds) {
        List<SysMenu> menuList = new ArrayList<>();
        double index = 0;
        for (Long deptId : deptIds) {
            menuList.add(new SysMenu().setId(deptId)
                .setSort(index));
            index++;
        }
        updateBatch(menuList);
    }

    @Nonnull
    private Function<SysMenu, TreeNode<Long>> getNodeFunction() {
        return menu -> {
            TreeNode<Long> node = new TreeNode<>();
            node.setId(menu.getId());
            node.setName(menu.getName());
            node.setParentId(menu.getParentId());
            node.setWeight(menu.getSort());
            // 扩展属性
            Map<String, Object> extra = new HashMap<>();
            extra.put("icon", menu.getIcon());
            extra.put("path", menu.getPath());
            extra.put("menuType", menu.getMenuType());
            extra.put("permission", menu.getPermission());
            extra.put("label", menu.getName());
            extra.put("sort", menu.getSort());
            node.setExtra(extra);
            return node;
        };
    }
}