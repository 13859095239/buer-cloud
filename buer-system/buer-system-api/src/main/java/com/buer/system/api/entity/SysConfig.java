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
 * 公共参数配置 Entity
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "公共参数配置表")
@Table("sys_config")
public class SysConfig extends SuperEntity<SysConfig> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @Schema(description = "id")
    private Long id;

    /**
     * 参数名称
     */
    @Schema(description = "参数名称")
    private String name;

    /**
     * 参数键名
     */
    @Schema(description = "参数键名")
    private String configKey;

    /**
     * 参数键值
     */
    @Schema(description = "参数键值")
    private String value;

    /**
     * 状态，1-启用，0-禁用
     */
    @Schema(description = "状态，1-启用，0-禁用")
    private String status;

    /**
     * 校验码
     */
    @Schema(description = "校验码")
    private String validateCode;

    /**
     * 是否为系统内置参数，1-是，0-否
     */
    @Schema(description = "是否为系统内置参数，1-是，0-否")
    private String systemFlag;

    /**
     * 参数类型，1-系统参数，2-业务参数
     */
    @Schema(description = "参数类型，1-系统参数，2-业务参数")
    private String publicType;

}