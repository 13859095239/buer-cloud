package com.buer.common.core.entity;

import com.mybatisflex.core.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体父类
 *
 * @author zoulan
 * @since 2023-06-08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class SuperEntity<T extends Model<T>> extends Model<T> implements Serializable {
    /**
     * 创建人
     */
    @Schema(description = "创建人ID")
    private String createUser;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @Schema(description = "最后更新人ID")
    private String updateUser;

    /**
     * 更新时间
     */
    @Schema(description = "最后更新时间")
    private LocalDateTime updateTime;

}
