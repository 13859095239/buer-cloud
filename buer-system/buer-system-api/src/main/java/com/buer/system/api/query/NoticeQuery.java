package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 通知公告 Query
 *
 * @author zoulan
 * @since 2023-09-02
 */
@Data
@Schema(description = "通知公告Query")
public class NoticeQuery implements Serializable {

    /**
     * 通知公告id列表
     */
    @Schema(description = "通知公告id列表")
    private String noticeIds;

    /**
     * 公告标题
     */
    @Schema(description = "公告标题")
    private String title;

    /**
     * 公告类型,字典
     */
    @Schema(description = "公告类型,字典")
    private String noticeType;


}