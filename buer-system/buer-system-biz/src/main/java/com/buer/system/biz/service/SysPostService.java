package com.buer.system.biz.service;

import com.buer.common.core.dto.TableEditorDTO;
import com.buer.system.api.entity.SysPost;
import com.buer.system.api.query.PostQuery;
import com.buer.system.api.vo.PostVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 岗位信息 Service
 *
 * @author zoulan
 * @since 2023-05-07
 */
public interface SysPostService extends IService<SysPost> {

    /**
     * 通过id查询岗位信息
     *
     * @param id id
     * @return 岗位信息
     */
    PostVO getPostById(Long id);

    /**
     * 新增岗位信息
     *
     * @param post 岗位对象
     * @return Boolean
     */
    boolean addPost(SysPost post);

    /**
     * 通过id修改岗位信息
     *
     * @param post 岗位对象
     * @return Boolean
     */
    boolean updatePost(SysPost post);

    /**
     * 通过id删除岗位信息
     *
     * @param postIds postIds
     * @return Boolean
     */
    boolean removePostByIds(List<Long> postIds);

    /**
     * 列表查询岗位信息
     *
     * @param postQuery 查询对象
     * @return 列表
     */
    List<PostVO> listPost(PostQuery postQuery);

    /**
     * 分页查询岗位信息
     *
     * @param page      分页对象
     * @param postQuery 查询对象
     * @return 分页对象
     */
    Page<PostVO> pagePost(Page<PostVO> page, PostQuery postQuery);

    /**
     * 通过编辑表格批量修改岗位
     *
     * @param tableEditorDTO 编辑表格对象
     * @return Boolean
     */
    Boolean updatePostByTableEditor(TableEditorDTO<SysPost> tableEditorDTO);
}