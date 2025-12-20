package com.buer.resource.biz.service;

import com.buer.resource.api.entity.SysOssConfig;
import com.buer.resource.api.query.OssConfigQuery;
import com.buer.resource.api.vo.OssConfigVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 文件配置 Service
 *
 * @author zoulan
 * @since 2023-08-27
 */
public interface SysOssConfigService extends IService<SysOssConfig> {

    /**
     * 初始化OSS配置
     */
    void init();

    /**
     * 通过id查询文件配置
     *
     * @param id 文件配置id
     * @return 文件配置信息
     */
    OssConfigVO getOssConfigById(Long id);

    /**
     * 新增文件配置
     *
     * @param entity SysOssConfig
     * @return Boolean
     */
    boolean addOssConfig(SysOssConfig entity);

    /**
     * 通过id修改文件配置
     *
     * @param entity SysOssConfig
     * @return Boolean
     */
    boolean updateOssConfig(SysOssConfig entity);

    /**
     * 通过id删除文件配置
     *
     * @param configIds configIds
     * @return Boolean
     */
    boolean removeOssConfigByIds(List<Long> configIds);

    /**
     * 列表查询文件配置
     *
     * @param entity 查询对象
     * @return 列表
     */
    List<OssConfigVO> listOssConfig(OssConfigQuery entity);

    /**
     * 分页查询文件配置
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    Page<OssConfigVO> pageOssConfig(Page<OssConfigVO> page, OssConfigQuery entity);

}