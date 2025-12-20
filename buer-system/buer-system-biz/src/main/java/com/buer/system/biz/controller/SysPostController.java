package com.buer.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.dto.TableEditorDTO;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.system.api.entity.SysPost;
import com.buer.system.api.query.PostQuery;
import com.buer.system.api.vo.PostVO;
import com.buer.system.biz.service.SysPostService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位 Controller
 *
 * @author zoulan
 * @since 2023-05-07
 */
@RestController
@RequestMapping("post")
@RequiredArgsConstructor
@Tag(name = "岗位")
public class SysPostController {

    private final SysPostService service;

    /**
     * 通过id查询岗位
     *
     * @param id id
     * @return PostVO
     */
    @Operation(summary = "通过id查询岗位")
    @GetMapping(value = "/{id}")
    public R<PostVO> getPostById(@PathVariable Long id) {
        return R.ok(service.getPostById(id));
    }

    /**
     * 新增岗位
     *
     * @param entity
     * @return Boolean
     */
    @Operation(summary = "新增岗位")
    @PostMapping
    @SaCheckPermission("post-add")
    public R<Boolean> addPost(@RequestBody SysPost entity) {
        return R.ok(service.addPost(entity));
    }

    /**
     * 通过id修改岗位
     *
     * @param entity
     * @return Boolean
     */
    @Operation(summary = "通过id修改岗位")
    @PutMapping
    @SaCheckPermission("post-edit")
    public R<Boolean> updatePost(@RequestBody SysPost entity) {
        return R.ok(service.updatePost(entity));
    }

    /**
     * 通过编辑表格批量修改岗位
     *
     * @param tableEditorDTO 编辑表格对象
     * @return Boolean
     */
    @Operation(summary = "通过id修改岗位")
    @PutMapping("table-editor")
    @SaCheckPermission("post-edit")
    public R<Boolean> updatePostByTableEditor(@RequestBody TableEditorDTO<SysPost> tableEditorDTO) {
        return R.ok(service.updatePostByTableEditor(tableEditorDTO));
    }

    /**
     * 通过id删除岗位
     *
     * @param postIds postIds
     * @return Boolean
     */
    @SysLog("删除岗位")
    @Operation(summary = "通过id删除岗位")
    @DeleteMapping
    @SaCheckPermission("post-delete")
    public R<Boolean> removePostByIds(@RequestBody List<Long> postIds) {
        return R.ok(service.removePostByIds(postIds));
    }

    /**
     * 列表查询岗位
     *
     * @param entity 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询岗位")
    @GetMapping(value = "/list")
    public R<List<PostVO>> listPost(PostQuery entity) {
        return R.ok(service.listPost(entity));
    }

    /**
     * 分页查询岗位
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询岗位")
    @GetMapping(value = "/page")
    public R<Page<PostVO>> pagePost(Page<PostVO> page, PostQuery entity) {
        return R.ok(service.pagePost(page, entity));
    }

}