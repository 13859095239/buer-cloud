package com.buer.system.api.entity;

import com.buer.common.core.entity.SuperEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 角色用户 Entity
 *
 * @author zoulan
 * @since 2021-10-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色用户")
@Table("sys_role_user")
public class SysRoleUser extends SuperEntity<SysRoleUser> {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Schema(description = "用户id")
    private Long userId;
    @Id
    @Schema(description = "角色id")
    private Long roleId;
}