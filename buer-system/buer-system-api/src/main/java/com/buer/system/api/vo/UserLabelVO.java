package com.buer.system.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户外键 VO
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Schema(description = "用户外键 VO")
public class UserLabelVO implements Serializable {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String nickname;


    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

}