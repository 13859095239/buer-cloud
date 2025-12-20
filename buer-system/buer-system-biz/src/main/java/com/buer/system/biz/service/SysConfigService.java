package com.buer.system.biz.service;

import com.buer.system.api.entity.SysConfig;
import com.buer.system.api.query.ConfigQuery;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 公共参数配置 Service
 *
 * @author zoulan
 * @since 2023-05-07
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 通过id查询公共参数配置
     *
     * @param id id
     * @return 系统公共参数信息
     */
    SysConfig getConfigById(Long id);

    /**
     * 通过id查询公共参数配置
     *
     * @param configKey configKey
     * @return 系统公共参数信息
     */
    SysConfig getConfigByKey(String configKey);

    /**
     * 新增公共参数配置
     *
     * @param config SysConfig对象
     * @return Boolean
     */
    boolean addConfig(SysConfig config);

    /**
     * 通过id修改公共参数配置
     *
     * @param config SysConfig对象
     * @return Boolean
     */
    boolean updateConfig(SysConfig config);

    /**
     * 通过id删除公共参数配置
     *
     * @param configIds configIds
     * @return Boolean
     */
    boolean removeConfigByIds(List<Long> configIds);

    /**
     * 列表查询公共参数配置
     *
     * @param configQuery 查询对象公共参数配置
     * @return 列表
     */
    List<SysConfig> listConfig(ConfigQuery configQuery);

    /**
     * 分页查询公共参数配置
     *
     * @param page        分页对象
     * @param configQuery 查询对象
     * @return 分页对象
     */
    Page<SysConfig> pageConfig(Page<SysConfig> page, ConfigQuery configQuery);

    /**
     * 刷新公共参数配置缓存
     */
    void resetConfigCache();
}