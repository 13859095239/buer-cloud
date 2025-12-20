package com.buer.system.biz.service;

import com.buer.system.api.dto.NoticeDTO;
import com.buer.system.api.entity.SysNotice;
import com.buer.system.api.query.NoticeQuery;
import com.buer.system.api.vo.NoticeVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 通知公告 Service
 *
 * @author zoulan
 * @since 2023-09-02
 */
public interface SysNoticeService extends IService<SysNotice> {

    /**
     * 通过id查询通知公告
     *
     * @param id id
     * @return 通知公告信息
     */
    NoticeVO getNoticeById(Long id);

    /**
     * 新增通知公告
     *
     * @param noticeDTO 通知公告对象
     * @return Boolean
     */
    boolean addNotice(NoticeDTO noticeDTO);

    /**
     * 通过id修改通知公告
     *
     * @param noticeDTO 通知公告对象
     * @return Boolean
     */
    boolean updateNotice(NoticeDTO noticeDTO);

    /**
     * 通过noticeIds删除通知公告
     *
     * @param noticeIds noticeIds
     * @return Boolean
     */
    boolean removeNoticeByIds(List<Long> noticeIds);

    /**
     * 列表查询通知公告
     *
     * @param noticeQuery 查询对象
     * @return 列表
     */
    List<NoticeVO> listNotice(NoticeQuery noticeQuery);

    /**
     * 分页查询通知公告
     *
     * @param page        分页对象
     * @param noticeQuery 查询对象
     * @return 分页对象
     */
    Page<NoticeVO> pageNotice(Page<NoticeVO> page, NoticeQuery noticeQuery);

}