package com.buer.system.api.vo;

import com.buer.system.api.entity.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户信息用于登录 VO
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode()
@Schema(description = "用户信息用于登录验证后VO")
public class UserForLoginVO implements Serializable {

    /**
     * 用户基本信息
     */
    private SysUser user;

    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色集合
     */
    private Long[] roles;


}
