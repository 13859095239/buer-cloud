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

package com.buer.common.core.constant;

/**
 * 通用常量
 *
 * @author zoulan
 * @since 2023-06-08
 */
public interface CommonConstants {

    String CLIENT_Id = "clientId";
    /**
     * 成功标记
     */
    Integer SUCCESS = 0;

    /**
     * 数据库查询批量处理时的批次大小，避免IN子句参数过多
     */
    int DB_QUERY_BATCH_SIZE = 1000;

}
