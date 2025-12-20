package com.buer.system.biz.service;

import com.buer.system.api.entity.SysOauthClient;
import com.buer.system.api.query.OauthClientQuery;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * oauth终端 Service
 *
 * @author zoulan
 * @since 2024-09-07
 */
public interface SysOauthClientService extends IService<SysOauthClient> {

    /**
     * 通过id查询oauth终端
     *
     * @param clientId 客户端ID
     * @return oauth终端信息
     */
    SysOauthClient getOauthClientDetailsByClientId(String clientId);

    /**
     * 新增oauth终端
     *
     * @param oauthClient oauth终端信息
     * @return boolean
     */
    boolean addOauthClient(SysOauthClient oauthClient);

    /**
     * 通过id修改oauth终端
     *
     * @param oauthClient oauth终端信息
     * @return boolean
     */
    boolean updateOauthClient(SysOauthClient oauthClient);

    /**
     * 通过id删除oauth终端
     *
     * @param oauthClientIds oauthClientIds
     * @return boolean
     */
    boolean removeOauthClientByIds(List<Long> oauthClientIds);

    /**
     * 列表查询oauth终端
     *
     * @param oauthClientQuery 查询对象
     * @return 列表
     */
    List<SysOauthClient> listOauthClient(OauthClientQuery oauthClientQuery);

    /**
     * 分页查询oauth终端
     *
     * @param page             分页对象
     * @param oauthClientQuery 查询对象
     * @return 分页对象
     */
    Page<SysOauthClient> pageOauthClient(Page<SysOauthClient> page, OauthClientQuery oauthClientQuery);

}