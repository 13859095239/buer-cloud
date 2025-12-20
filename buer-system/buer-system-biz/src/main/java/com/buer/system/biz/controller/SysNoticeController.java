package com.buer.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.system.api.dto.NoticeDTO;
import com.buer.system.api.query.NoticeQuery;
import com.buer.system.api.vo.NoticeVO;
import com.buer.system.biz.service.SysNoticeService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知公告 Controller
 *
 * @author zoulan
 * @since 2023-09-02
 */
@RestController
@RequestMapping("notice")
@RequiredArgsConstructor
@Tag(name = "通知公告")
public class SysNoticeController {

    private final SysNoticeService service;

    /**
     * 通过id查询通知公告
     *
     * @param id id
     * @return NoticeVO
     */
    @Operation(summary = "通过id查询通知公告")
    @GetMapping(value = "/{id}")
    public R<NoticeVO> getNoticeById(@PathVariable Long id) {
        return R.ok(service.getNoticeById(id));
    }

    /**
     * 新增通知公告
     *
     * @param noticeDTO NoticeDTO对象
     * @return Boolean
     */
    @Operation(summary = "新增通知公告")
    @PostMapping
    @SaCheckPermission("notice-add")
    public R<Boolean> addNotice(@Validated @RequestBody NoticeDTO noticeDTO) {
        return R.ok(service.addNotice(noticeDTO));
    }

    /**
     * 通过id修改通知公告
     *
     * @param noticeDTO NoticeDTO对象
     * @return Boolean
     */
    @Operation(summary = "通过id修改通知公告")
    @PutMapping
    @SaCheckPermission("notice-edit")
    public R<Boolean> updateNotice(@Validated @RequestBody NoticeDTO noticeDTO) {
        return R.ok(service.updateNotice(noticeDTO));
    }

    /**
     * 通过id删除通知公告
     *
     * @param noticeIds noticeIds
     * @return Boolean
     */
    @SysLog("删除公告")
    @Operation(summary = "通过id删除通知公告")
    @DeleteMapping
    @SaCheckPermission("notice-delete")
    public R<Boolean> removeNoticeByIds(@RequestBody List<Long> noticeIds) {
        return R.ok(service.removeNoticeByIds(noticeIds));
    }

    /**
     * 列表查询通知公告
     *
     * @param entity 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询通知公告")
    @GetMapping(value = "/list")
    public R<List<NoticeVO>> listNotice(NoticeQuery entity) {
        return R.ok(service.listNotice(entity));
    }

    /**
     * 分页查询通知公告
     *
     * @param page        分页对象
     * @param noticeQuery 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询通知公告")
    @GetMapping(value = "/page")
    public R<Page<NoticeVO>> pageNotice(Page<NoticeVO> page, NoticeQuery noticeQuery) {
        return R.ok(service.pageNotice(page, noticeQuery));
    }

}