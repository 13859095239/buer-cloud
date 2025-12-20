package com.buer.system.biz.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.buer.common.core.util.StringUtils;
import com.buer.resource.api.dto.OssFileGroupDTO;
import com.buer.resource.api.feign.RemoteOssService;
import com.buer.system.api.dto.NoticeDTO;
import com.buer.system.api.entity.SysNotice;
import com.buer.system.api.filler.DictLabelFiller;
import com.buer.system.api.filler.UserLabelFiller;
import com.buer.system.api.query.NoticeQuery;
import com.buer.system.api.vo.NoticeVO;
import com.buer.system.biz.mapper.SysNoticeMapper;
import com.buer.system.biz.service.SysNoticeService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.buer.system.api.entity.table.SysNoticeTableDef.SYS_NOTICE;

/**
 * 通知公告 ServiceImpl
 *
 * @author zoulan
 * @since 2023-09-02
 */
@Service
@RequiredArgsConstructor
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {

    private final RemoteOssService remoteOssService;
    private final DictLabelFiller dictLabelFiller;
    private final UserLabelFiller userLabelFiller;

    /**
     * 通过id查询通知公告
     *
     * @param id id
     * @return NoticeVO
     */
    @Override
    public NoticeVO getNoticeById(Long id) {
        return getQueryChain().and(SYS_NOTICE.ID.eq(id))
            .oneAs(NoticeVO.class);
    }

    /**
     * 新增通知公告
     *
     * @param noticeDTO 通知公告对象
     * @return Boolean
     */
    @Override
    public boolean addNotice(NoticeDTO noticeDTO) {
        if (StrUtil.isNotEmpty(noticeDTO.getFileGroupData())) {
            long id = IdUtil.getSnowflakeNextId();
            noticeDTO.setFileGroupId(id);
            remoteOssService.updateFileGroup(new OssFileGroupDTO().setFileGroupId(id)
                .setIds(noticeDTO.getFileGroupData()));
        }
        return save(noticeDTO);
    }

    /**
     * 通过id修改通知公告
     *
     * @param noticeDTO 通知公告对象
     * @return Boolean
     */
    @Override
    public boolean updateNotice(NoticeDTO noticeDTO) {
        if (noticeDTO.getFileGroupId() == null) {
            noticeDTO.setFileGroupId(IdUtil.getSnowflakeNextId());
        }
        remoteOssService.updateFileGroup(new OssFileGroupDTO().setFileGroupId(noticeDTO.getFileGroupId())
            .setIds(noticeDTO.getFileGroupData()));
        return updateById(noticeDTO);
    }

    /**
     * 通过noticeIds删除通知公告
     *
     * @param noticeIds noticeIds
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean removeNoticeByIds(List<Long> noticeIds) {
        // List<String> noticeIdList = U.getIdListByStringWithEmptyThrow(noticeIds, "通知公告Id");
        return removeByIds(noticeIds);
    }

    /**
     * 列表查询通知公告
     *
     * @param noticeQuery 查询对象
     * @return 列表
     */
    @Override
    public List<NoticeVO> listNotice(NoticeQuery noticeQuery) {
        return queryChain().listAs(NoticeVO.class);
    }

    /**
     * 分页查询通知公告
     *
     * @param page        分页对象
     * @param noticeQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<NoticeVO> pageNotice(Page<NoticeVO> page, NoticeQuery noticeQuery) {
        Page<NoticeVO> dataPage = getQueryChainByQuery(noticeQuery)
            .pageAs(page, NoticeVO.class);
        dictLabelFiller.fillField(
            dataPage.getRecords(),
            "noticeType",
            NoticeVO::getNoticeType,
            NoticeVO::setNoticeTypeName,
            NoticeVO::setNoticeTypeTheme
        );
        userLabelFiller.fillField(dataPage.getRecords(),
            NoticeVO::getCreateUser,
            NoticeVO::setCreateUserName);
        return dataPage;
    }

    /**
     * 获取QueryChain对象
     */
    private QueryChain<SysNotice> getQueryChain() {
        return queryChain().select(SYS_NOTICE.ALL_COLUMNS);
    }

    /**
     * 通过query获取QueryChain对象
     */
    private QueryChain<SysNotice> getQueryChainByQuery(NoticeQuery noticeQuery) {
        return getQueryChain()
            .and(SYS_NOTICE.ID.in(StringUtils.arrayBySplit(noticeQuery.getNoticeIds())))
            .and(SYS_NOTICE.TITLE.like(noticeQuery.getTitle()))
            .and(SYS_NOTICE.NOTICE_TYPE.eq(noticeQuery.getNoticeType()))
            .orderBy(SYS_NOTICE.CREATE_TIME.desc());
    }
}