/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.buer.system.api.constants;

import com.buer.common.core.entity.BaseEnum;
import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型
 *
 * @author zoulan
 * @since 2023-06-08
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum implements BaseEnum<String> {

    /**
     * 目录
     */
    PATH("0", "目录"),

    /**
     * 菜单
     */
    MENU("1", "菜单"),

    /**
     * 权限
     */
    PERMISSION("2", "权限");

    /**
     * 枚举值
     */
    @EnumValue
    private final String code;
    /**
     * 枚举描述
     */
    private final String desc;

}
