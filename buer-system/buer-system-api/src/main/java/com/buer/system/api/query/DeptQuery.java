package com.buer.system.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门 Query
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Data
@Schema(description = "部门Query")
public class DeptQuery implements Serializable {

    /**
     * 部门id列表
     */
    @Schema(description = "部门id列表")
    private String deptIds;

    /**
     * 名称
     */
    @Schema(description = "部门名称")
    private String name;

}