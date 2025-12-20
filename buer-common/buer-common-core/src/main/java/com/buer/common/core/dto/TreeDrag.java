package com.buer.common.core.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 树拖动对象
 *
 * @author zoulan
 * @since 2024-05-01
 */
@Data
@Accessors(chain = true)
public class TreeDrag implements Serializable {
    private String sourceId;
    private String targetId;
    private String dragType;
}
