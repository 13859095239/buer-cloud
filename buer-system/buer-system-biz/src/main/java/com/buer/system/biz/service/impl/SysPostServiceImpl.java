package com.buer.system.biz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.buer.common.core.dto.TableEditorDTO;
import com.buer.common.core.util.StringUtils;
import com.buer.system.api.entity.SysPost;
import com.buer.system.api.query.PostQuery;
import com.buer.system.api.vo.PostVO;
import com.buer.system.biz.mapper.SysPostMapper;
import com.buer.system.biz.service.SysPostService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.buer.system.api.entity.table.SysPostTableDef.SYS_POST;

/**
 * 岗位 ServiceImpl
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Service
@RequiredArgsConstructor
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {

    /**
     * 通过id查询岗位
     *
     * @param id id
     * @return PostVO
     */
    @Override
    public PostVO getPostById(Long id) {
        return getQueryChain().and(SYS_POST.ID.eq(id))
            .oneAs(PostVO.class);
    }

    /**
     * 新增岗位
     *
     * @param post 岗位对象
     * @return Boolean
     */
    @Override
    public boolean addPost(SysPost post) {
        return save(post);
    }

    /**
     * 通过id修改岗位
     *
     * @param post 岗位对象
     * @return Boolean
     */
    @Override
    public boolean updatePost(SysPost post) {
        return updateById(post);
    }

    /**
     * 通过id删除岗位
     *
     * @param postIds postIds
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean removePostByIds(List<Long> postIds) {
        // List<String> postIdList = U.getIdListByStringWithEmptyThrow(postIds, "岗位Id");
        return removeByIds(postIds);
    }

    /**
     * 列表查询岗位
     *
     * @param postQuery 查询对象
     * @return 列表
     */
    @Override
    public List<PostVO> listPost(PostQuery postQuery) {
        return getQueryChainByQuery(postQuery).listAs(PostVO.class);

    }

    /**
     * 分页查询岗位
     *
     * @param page      分页对象
     * @param postQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<PostVO> pagePost(Page<PostVO> page, PostQuery postQuery) {
        return getQueryChainByQuery(postQuery).pageAs(page, PostVO.class);
    }

    /**
     * 获取QueryChain对象
     */
    private QueryChain<SysPost> getQueryChain() {
        return queryChain().select(SYS_POST.ALL_COLUMNS);
    }

    /**
     * 通过query获取QueryChain对象
     */
    private QueryChain<SysPost> getQueryChainByQuery(PostQuery postQuery) {
        return getQueryChain()
            .and(SYS_POST.ID.in(StringUtils.arrayBySplit(postQuery.getPostIds())))
            .and(SYS_POST.NAME.like(postQuery.getName()))
            .orderBy(SYS_POST.CREATE_TIME.desc());
    }

    /**
     * 通过编辑表格批量修改岗位
     *
     * @param tableEditorDTO 编辑表格对象
     * @return Boolean
     */
    @Override
    @Transactional
    public Boolean updatePostByTableEditor(TableEditorDTO<SysPost> tableEditorDTO) {
        List<String> deleteKeys = StrUtil.splitTrim(tableEditorDTO.getDeleteKeys(), ",");
        List<SysPost> addList = tableEditorDTO.getAddList();
        List<SysPost> updateList = tableEditorDTO.getUpdateList();
        if (!deleteKeys.isEmpty()) {
            removeByIds(deleteKeys);
        }
        if (!addList.isEmpty()) {
            saveBatch(addList);
        }
        if (!updateList.isEmpty()) {
            updateBatch(updateList);
        }
        return true;
    }

}