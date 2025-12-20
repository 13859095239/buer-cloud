package com.buer.common.oss.factory;

import cn.hutool.core.util.StrUtil;
import com.buer.common.core.constant.CacheConstants;
import com.buer.common.oss.constant.OssConstant;
import com.buer.common.oss.core.OssClient;
import com.buer.common.oss.exception.OssException;
import com.buer.common.redis.util.RedisUtils;
import com.buer.resource.api.entity.SysOssConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件上传Factory
 *
 * @author Lion Li
 */
@Slf4j
public class OssFactory {

    private static final Map<String, OssClient> CLIENT_CACHE = new ConcurrentHashMap<>();

    /**
     * 获取默认实例
     */
    public static OssClient instance() {
        // 获取redis 默认类型
        String configKey = RedisUtils.getCacheObject(OssConstant.DEFAULT_CONFIG_KEY);
        if (StrUtil.isEmpty(configKey)) {
            throw new OssException("文件存储服务类型无法找到!");
        }
        return instance(configKey);
    }

    /**
     * 根据类型获取实例
     */
    public static OssClient instance(String configKey) {
        SysOssConfig properties = RedisUtils.getCacheObject(CacheConstants.OSS_CONFIG + ":" + configKey);
        if (properties == null) {
            throw new OssException("系统异常, '" + configKey + "'配置信息不存在!");
        }
        OssClient client = CLIENT_CACHE.get(configKey);
        if (client == null) {
            CLIENT_CACHE.put(configKey, new OssClient(configKey, properties));
            log.info("创建OSS实例 key => {}", configKey);
            return CLIENT_CACHE.get(configKey);
        }
        // 配置不相同则重新构建
        if (!client.checkPropertiesSame(properties)) {
            CLIENT_CACHE.put(configKey, new OssClient(configKey, properties));
            log.info("重载OSS实例 key => {}", configKey);
            return CLIENT_CACHE.get(configKey);
        }
        return client;
    }

}
