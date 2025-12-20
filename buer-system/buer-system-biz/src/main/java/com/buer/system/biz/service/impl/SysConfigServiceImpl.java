package com.buer.system.biz.service.impl;

import com.buer.common.core.constant.CacheConstants;
import com.buer.common.core.util.StringUtils;
import com.buer.common.redis.util.CacheUtils;
import com.buer.system.api.entity.SysConfig;
import com.buer.system.api.query.ConfigQuery;
import com.buer.system.biz.mapper.SysConfigMapper;
import com.buer.system.biz.service.SysConfigService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.buer.system.api.entity.table.SysConfigTableDef.SYS_CONFIG;
import static com.buer.system.api.entity.table.SysNoticeTableDef.SYS_NOTICE;

/**
 * 公共参数配置 ServiceImpl
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    /**
     * 通过id查询公共参数配置
     *
     * @param id id
     * @return PublicParamVO
     */
    @Override
    public SysConfig getConfigById(Long id) {
        return queryChain().and(SYS_CONFIG.ID.eq(id))
            .one();
    }

    /**
     * 通过id查询公共参数配置
     *
     * @param configKey configKey
     * @return 系统公共参数信息
     */
    @Override
    @Cacheable(value = CacheConstants.CONFIG, key = "#configKey", unless = "#result == null ")
    public SysConfig getConfigByKey(String configKey) {
        return queryChain().and(SYS_CONFIG.CONFIG_KEY.eq(configKey))
            .one();
    }

    /**
     * 新增公共参数配置
     *
     * @param config SysConfig对象
     * @return Boolean
     */
    @Override
    public boolean addConfig(SysConfig config) {
        return save(config);
    }

    /**
     * 通过id修改公共参数配置
     *
     * @param config SysConfig对象
     * @return Boolean
     */
    @Override
    public boolean updateConfig(SysConfig config) {
        SysConfig oldConfig = getConfigById(config.getId());
        // 清空之前的缓存
        String configKey = oldConfig.getConfigKey();
        CacheUtils.evict(CacheConstants.CONFIG, configKey);
        if (!configKey.equals(config.getConfigKey())) {
            CacheUtils.evict(CacheConstants.CONFIG, config.getConfigKey());
        }
        return updateById(config);
    }

    /**
     * 通过id删除公共参数配置
     *
     * @param configIds configIds
     * @return Boolean
     */
    @Override
    @Transactional
    @CacheEvict(value = CacheConstants.CONFIG, allEntries = true)
    public boolean removeConfigByIds(List<Long> configIds) {
        // List<String> configIdList = U.getIdListByStringWithEmptyThrow(configIds, "公共参数配置Id");
        removeByIds(configIds);
        configIds.forEach(id -> CacheUtils.evict(CacheConstants.CONFIG, id));
        return true;
    }

    /**
     * 列表查询公共参数配置
     *
     * @param configQuery 查询对象
     * @return 列表
     */
    @Override
    public List<SysConfig> listConfig(ConfigQuery configQuery) {
        return getQueryChainByQuery(configQuery).list();
    }

    /**
     * 分页查询公共参数配置
     *
     * @param page        分页对象
     * @param configQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<SysConfig> pageConfig(Page<SysConfig> page, ConfigQuery configQuery) {
        return getQueryChainByQuery(configQuery).page(page);
    }

    /**
     * 刷新公共参数配置缓存
     */
    @Override
    public void resetConfigCache() {
        CacheUtils.clear(CacheConstants.CONFIG);
        List<SysConfig> configs = listConfig(new ConfigQuery());
        configs.forEach(config -> CacheUtils.put(CacheConstants.CONFIG, config.getConfigKey(), config));
    }

    /**
     * 获取QueryChain对象
     */
    private QueryChain<SysConfig> getQueryChain() {
        return queryChain().select(SYS_CONFIG.ALL_COLUMNS);
    }

    /**
     * 通过query获取QueryChain对象
     */
    private QueryChain<SysConfig> getQueryChainByQuery(ConfigQuery configQuery) {
        return getQueryChain()
            .and(SYS_CONFIG.ID.in(StringUtils.arrayBySplit(configQuery.getConfigIds())))
            .and(SYS_CONFIG.CONFIG_KEY.like(configQuery.getConfigKey()))
            .and(SYS_CONFIG.NAME.like(configQuery.getName()))
            .orderBy(SYS_CONFIG.CREATE_TIME.desc());
    }
}