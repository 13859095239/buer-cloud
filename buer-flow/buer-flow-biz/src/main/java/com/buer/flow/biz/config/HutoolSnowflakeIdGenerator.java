package com.buer.flow.biz.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.flowable.common.engine.impl.cfg.IdGenerator;


/**
 * 使用 Hutool 雪花算法的 Flowable IdGenerator
 */
public class HutoolSnowflakeIdGenerator implements IdGenerator {

    private final Snowflake snowflake;

    public HutoolSnowflakeIdGenerator(long workerId, long datacenterId) {
        this.snowflake = IdUtil.createSnowflake(workerId, datacenterId);
    }

    @Override
    public String getNextId() {
        // 返回 Long 转字符串，兼容 Flowable 默认 varchar(64)
        return String.valueOf(snowflake.nextId());
    }
}