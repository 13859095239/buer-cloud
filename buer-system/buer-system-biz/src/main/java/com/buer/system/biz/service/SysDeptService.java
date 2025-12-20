package com.buer.system.biz.service;

import cn.hutool.core.lang.tree.Tree;
import com.buer.system.api.entity.SysDept;
import com.buer.system.api.query.DeptQuery;
import com.buer.system.api.query.DeptTreeQuery;
import com.buer.system.api.vo.DeptVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 部门 Service
 *
 * @author zoulan
 * @since 2023-05-06
 */
public interface SysDeptService extends IService<SysDept> {
    /**
     * 通过id查询部门
     *
     * @param id id
     * @return 部门信息
     */
    DeptVO getDeptById(Long id);

    /**
     * 新增部门
     *
     * @param dept dept对象
     * @return Boolean
     */
    Long addDept(SysDept dept);

    /**
     * 通过id修改部门
     *
     * @param dept dept对象
     * @return Boolean
     */
    boolean updateDept(SysDept dept);

    /**
     * 通过id删除部门
     *
     * @param deptIds deptIds
     * @return Boolean
     */
    boolean removeDeptByIds(List<Long> deptIds);

    /**
     * 列表查询部门
     *
     * @param deptQuery 查询对象
     * @return 列表
     */
    List<DeptVO> listDept(DeptQuery deptQuery);

    /**
     * 分页查询部门
     *
     * @param page      分页对象
     * @param deptQuery 查询对象
     * @return 分页对象
     */
    Page<DeptVO> pageDept(Page<DeptVO> page, DeptQuery deptQuery);

    /**
     * 查询部门树
     *
     * @param deptTreeQuery 查询对象
     * @return 树对象
     */
    List<Tree<Long>> treeDept(DeptTreeQuery deptTreeQuery);

    /**
     * 更新部门树节点排序
     *
     * @param deptIds 需要更新部门ids排序
     */
    void updateDeptTreeSort(List<Long> deptIds);
}