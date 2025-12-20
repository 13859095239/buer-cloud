package com.buer.system.api.entity;

import com.buer.common.core.entity.SuperEntity;
import com.buer.common.core.xss.Xss;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 通知公告 Entity
 *
 * @author zoulan
 * @since 2023-09-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "通知公告")
@Table("sys_notice")
public class SysNotice extends SuperEntity<SysNotice> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 公告ID
     */
    @Id
    @Schema(description = "公告ID")
    private Long id;

    /**
     * 公告标题
     */
    @Schema(description = "公告标题")
    @Xss(message = "公告标题不能包含脚本字符")
    private String title;

    /**
     * 公告类型,字典
     */
    @Schema(description = "公告类型,字典")
    @Xss(message = "公告类型不能包含脚本字符")
    private String noticeType;

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
     * 删除标记
     */
    @Schema(description = "删除标记")
    private String delFlag;

}