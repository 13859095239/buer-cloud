package com.buer.resource.biz.service.impl;


import com.buer.common.core.constant.CacheConstants;
import com.buer.common.oss.constant.OssConstant;
import com.buer.common.redis.util.RedisUtils;
import com.buer.resource.api.entity.SysOssConfig;
import com.buer.resource.api.query.OssConfigQuery;
import com.buer.resource.api.vo.OssConfigVO;
import com.buer.resource.biz.mapper.SysOssConfigMapper;
import com.buer.resource.biz.service.SysOssConfigService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.buer.resource.api.entity.table.SysOssConfigTableDef.SYS_OSS_CONFIG;

/**
 * 对象存储配置表 ServiceImpl
 *
 * @author zoulan
 * @since 2023-08-27
 */
@Service
@RequiredArgsConstructor
public class SysOssConfigServiceImpl extends ServiceImpl<SysOssConfigMapper, SysOssConfig> implements SysOssConfigService {

    /**
     * 项目启动时，初始化参数到缓存，加载配置类
     */
    @Override
    public void init() {
        List<SysOssConfig> list = list();
        for (SysOssConfig config : list) {
            String configKey = config.getConfigKey();
            if ("0".equals(config.getStatus())) {
                RedisUtils.setCacheObject(OssConstant.DEFAULT_CONFIG_KEY, configKey);
            }
            RedisUtils.setObject(CacheConstants.OSS_CONFIG + ":" + config.getConfigKey(), config);
        }
    }

    /**
     * 通过id查询文件配置
     *
     * @param id 文件配置id
     * @return OssConfigVO
     */
    @Override
    public OssConfigVO getOssConfigById(Long id) {
        return queryChain().and(SYS_OSS_CONFIG.ID.eq(id))
            .oneAs(OssConfigVO.class);
    }

    /**
     * 新增文件配置
     *
     * @param entity SysOssConfig
     * @return Boolean
     */
    @Override
    public boolean addOssConfig(SysOssConfig entity) {
        return save(entity);
    }

    /**
     * 通过id修改文件配置
     *
     * @param entity SysOssConfig
     * @return Boolean
     */
    @Override
    public boolean updateOssConfig(SysOssConfig entity) {
        return updateById(entity);
    }

    /**
     * 通过id删除文件配置
     *
     * @param configIds configIds
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean removeOssConfigByIds(List<Long> configIds) {
        // List<String> idList = U.getIdListByStringWithEmptyThrow(configIds, "文件配置Id");
        return removeByIds(configIds);
    }

    /**
     * 列表查询文件配置
     *
     * @param entity 查询对象
     * @return 列表
     */
    @Override
    public List<OssConfigVO> listOssConfig(OssConfigQuery entity) {
        return getQueryChainByQuery(entity).listAs(OssConfigVO.class);
    }

    /**
     * 分页查询文件配置
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Override
    public Page<OssConfigVO> pageOssConfig(Page<OssConfigVO> page, OssConfigQuery entity) {
        return getQueryChainByQuery(entity).pageAs(page, OssConfigVO.class);
    }

    /**
     * 获取QueryChain对象
     */
    private QueryChain<SysOssConfig> getQueryChainByQuery(OssConfigQuery entity) {
        return queryChain()
            .and(SYS_OSS_CONFIG.CONFIG_KEY.eq(entity.getConfigKey()));
    }
}