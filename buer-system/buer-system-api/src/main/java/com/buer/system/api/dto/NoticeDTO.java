package com.buer.system.api.dto;

import com.buer.system.api.entity.SysNotice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 通知公告 DTO
 *
 * @author zoulan
 * @since 2023-09-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "通知公告")
public class NoticeDTO extends SysNotice {

    /**
     * 文件组数据
     */
    @Schema(description = "文件组数据")
    private String fileGroupData;

}