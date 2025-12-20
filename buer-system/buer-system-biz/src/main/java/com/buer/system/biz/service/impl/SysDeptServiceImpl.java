package com.buer.system.biz.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.buer.common.core.constant.CacheConstants;
import com.buer.common.redis.util.CacheUtils;
import com.buer.system.api.entity.SysDept;
import com.buer.system.api.query.DeptQuery;
import com.buer.system.api.query.DeptTreeQuery;
import com.buer.system.api.vo.DeptVO;
import com.buer.system.biz.mapper.SysDeptMapper;
import com.buer.system.biz.service.SysDeptService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.buer.system.api.entity.table.SysDeptTableDef.SYS_DEPT;

/**
 * 部门 ServiceImpl
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    /**
     * 通过id查询部门
     *
     * @param id id
     * @return DeptVO
     */
    @Cacheable(value = CacheConstants.DEPT_DETAILS, key = "#id")
    @Override
    public DeptVO getDeptById(Long id) {
        return queryChain().and(SYS_DEPT.ID.eq(id))
            .oneAs(DeptVO.class);
    }

    /**
     * 新增部门
     *
     * @param dept 部门对象
     * @return Boolean
     */
    @Override
    public Long addDept(SysDept dept) {
        save(dept);
        return dept.getId();
    }


    /**
     * 通过id修改部门
     *
     * @param dept 部门对象
     * @return Boolean
     */
    @Override
    @CacheEvict(value = CacheConstants.DEPT_DETAILS, key = "#dept.id")
    public boolean updateDept(SysDept dept) {
        return updateById(dept);
    }

    /**
     * 通过id删除部门
     *
     * @param deptIds deptIds
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean removeDeptByIds(List<Long> deptIds) {
        // List<String> deptIdList = U.getIdListByStringWithEmptyThrow(deptIds, "部门Id");
        removeByIds(deptIds);
        deptIds.forEach(id -> CacheUtils.evict(CacheConstants.DEPT_DETAILS, id));
        return true;
    }

    /**
     * 列表查询部门
     *
     * @param deptQuery 查询对象
     * @return 列表
     */
    @Override
    public List<DeptVO> listDept(DeptQuery deptQuery) {
        return queryChain().listAs(DeptVO.class);
    }

    /**
     * 分页查询部门
     *
     * @param page      分页对象
     * @param deptQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<DeptVO> pageDept(Page<DeptVO> page, DeptQuery deptQuery) {
        return queryChain().and(SYS_DEPT.NAME.like(deptQuery.getName()))
            .pageAs(page, DeptVO.class);
    }

    /**
     * 查询部门树
     *
     * @param deptTreeQuery 查询对象
     * @return 树对象
     */
    @Override
    public List<Tree<Long>> treeDept(DeptTreeQuery deptTreeQuery) {
        List<TreeNode<Long>> nodes = list(QueryWrapper.create()
            .isNull(SysDept::getParentId, deptTreeQuery.getIsLazy() && deptTreeQuery.getParentId() == null)
            .eq(SysDept::getParentId, deptTreeQuery.getParentId(), deptTreeQuery.getIsLazy())).stream()
            .map(getNodeFunction())
            .collect(Collectors.toList());
        List<Tree<Long>> tree = TreeUtil.build(nodes, deptTreeQuery.getParentId());
        return ObjectUtil.defaultIfNull(tree, new ArrayList<>());
    }

    /**
     * 更新部门树节点排序
     *
     * @param deptIds 需要更新部门ids排序
     */
    @Override
    public void updateDeptTreeSort(List<Long> deptIds) {
        // List<String> deptIdList = U.getIdListByStringWithEmptyThrow(deptIds, "部门Id");
        List<SysDept> deptList = new ArrayList<>();
        double index = 0;
        for (Long deptId : deptIds) {
            deptList.add(new SysDept().setId(deptId)
                .setSort(index));
            index++;
        }
        updateBatch(deptList);
    }

    @Nonnull
    private Function<SysDept, TreeNode<Long>> getNodeFunction() {
        return menu -> {
            TreeNode<Long> node = new TreeNode<>();
            node.setId(menu.getId());
            node.setName(menu.getName());
            node.setParentId(menu.getParentId());
            node.setWeight(menu.getSort());
            Map<String, Object> extra = new HashMap<>();
            extra.put("sort", menu.getSort());
            extra.put("depiction", menu.getDepiction());
            node.setExtra(extra);
            return node;
        };
    }
}