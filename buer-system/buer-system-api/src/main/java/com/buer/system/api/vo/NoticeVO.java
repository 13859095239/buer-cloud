package com.buer.system.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通知公告 VO
 *
 * @author zoulan
 * @since 2023-09-02
 */
@Data
@Schema(description = "通知公告 VO")
public class NoticeVO implements Serializable {


    /**
     * 公告ID
     */
    @Schema(description = "公告ID")
    private Long id;

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

    /**
     * 公告类型名称,字典
     */
    @Schema(description = "公告类型名称")
    private String noticeTypeName;

    /**
     * 公告类型名称,字典
     */
    @Schema(description = "公告类型主题")
    private String noticeTypeTheme;

    /**
     * 公告内容
     */
    @Schema(description = "公告内容")
    private String content;

    /**
     * 文件组id
     */
    @Schema(description = "文件组id")
    private Long fileGroupId;

    /**
     * 公告状态（0正常 1关闭）
     */
    @Schema(description = "公告状态（0正常 1关闭）")
    private String status;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private String createUser;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createUserName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}