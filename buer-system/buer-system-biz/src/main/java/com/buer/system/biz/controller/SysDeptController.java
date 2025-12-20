package com.buer.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.system.api.entity.SysDept;
import com.buer.system.api.query.DeptQuery;
import com.buer.system.api.query.DeptTreeQuery;
import com.buer.system.api.vo.DeptVO;
import com.buer.system.biz.service.SysDeptService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门 Controller
 *
 * @author zoulan
 * @since 2023-05-06
 */
@RestController
@RequestMapping("dept")
@RequiredArgsConstructor
@Tag(name = "部门")
public class SysDeptController {
    private final SysDeptService service;

    /**
     * 通过id查询部门
     *
     * @param id id
     * @return DeptVO
     */
    @Operation(summary = "通过id查询部门")
    @GetMapping(value = "/{id}")
    public R<DeptVO> getDeptById(@PathVariable Long id) {
        return R.ok(service.getDeptById(id));
    }

    /**
     * 新增部门
     *
     * @param dept dept对象
     * @return Boolean
     */
    @Operation(summary = "新增部门")
    @PostMapping
    @SaCheckPermission("dept-add")
    public R<Long> addDept(@RequestBody SysDept dept) {
        return R.ok(service.addDept(dept));
    }

    /**
     * 通过id修改部门
     *
     * @param dept dept对象
     * @return Boolean
     */
    @Operation(summary = "通过id修改部门")
    @PutMapping
    @SaCheckPermission("dept-edit")
    public R<Boolean> updateDept(@RequestBody SysDept dept) {
        return R.ok(service.updateDept(dept));
    }

    /**
     * 通过id删除部门
     *
     * @param deptIds deptIds
     * @return Boolean
     */
    @SysLog("删除部门")
    @Operation(summary = "通过id删除部门")
    @DeleteMapping
    @SaCheckPermission("dept-delete")
    public R<Boolean> removeDeptByIds(@RequestBody List<Long> deptIds) {
        return R.ok(service.removeDeptByIds(deptIds));
    }

    /**
     * 列表查询部门
     *
     * @param entity 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询部门")
    @GetMapping(value = "/list")
    public R<List<DeptVO>> listDept(DeptQuery entity) {
        return R.ok(service.listDept(entity));
    }

    /**
     * 分页查询部门
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询部门")
    @GetMapping(value = "/page")
    public R<Page<DeptVO>> pageDept(Page<DeptVO> page, DeptQuery entity) {
        return R.ok(service.pageDept(page, entity));
    }

    /**
     * 查询部门树数据
     *
     * @param deptTreeQuery 查询对象
     * @return Tree
     */
    @Operation(summary = "查询部门树数据")
    @GetMapping("/tree")
    public R<List<Tree<Long>>> treeDept(DeptTreeQuery deptTreeQuery) {
        return R.ok(service.treeDept(deptTreeQuery));
    }

    /**
     * 更新部门树节点排序
     *
     * @param deptIds 需要更新部门ids排序
     */
    @Operation(summary = "更新部门树节点排序")
    @PutMapping("/updateDeptTreeSort")
    public R updateDeptTreeSort(@RequestBody List<@NotNull Long> deptIds) {
        service.updateDeptTreeSort(deptIds);
        return R.ok();
    }
}