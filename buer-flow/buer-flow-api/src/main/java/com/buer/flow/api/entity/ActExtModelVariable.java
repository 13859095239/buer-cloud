package com.buer.flow.api.entity;

import com.buer.common.core.entity.SuperEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 模型变量
 *
 * @author zoulan
 * @since 2024-05-16
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "模型变量")
@Table("act_ext_model_variable")
public class ActExtModelVariable extends SuperEntity<ActExtModelVariable> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @Schema(description = "id")
    private Long id;

    /**
     * proc_inst_id
     */
    @Schema(description = "proc_inst_id")
    private String modelId;

    /**
     * 变量标识
     */
    @Schema(description = "变量编码")
    private String code;

    /**
     * 变量名称
     */
    @Schema(description = "变量名称")
    private String name;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String depiction;

}