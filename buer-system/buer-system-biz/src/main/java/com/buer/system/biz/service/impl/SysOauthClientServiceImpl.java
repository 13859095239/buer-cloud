package com.buer.system.biz.service.impl;

import com.buer.system.api.entity.SysOauthClient;
import com.buer.system.api.query.OauthClientQuery;
import com.buer.system.biz.mapper.SysOauthClientMapper;
import com.buer.system.biz.service.SysOauthClientService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.buer.system.api.entity.table.SysOauthClientTableDef.SYS_OAUTH_CLIENT;

/**
 * oauth终端 ServiceImpl
 *
 * @author zoulan
 * @since 2024-09-07
 */
@Service
@RequiredArgsConstructor
public class SysOauthClientServiceImpl extends ServiceImpl<SysOauthClientMapper, SysOauthClient> implements SysOauthClientService {

    /**
     * 通过id查询oauth终端
     *
     * @param clientId 客户端ID
     * @return SysOauthClient
     */
    @Override
    public SysOauthClient getOauthClientDetailsByClientId(String clientId) {
        return queryChain().and(SYS_OAUTH_CLIENT.CLIENT_ID.eq(clientId))
            .one();
    }

    /**
     * 新增oauth终端
     *
     * @param oauthClient oauth终端信息
     * @return boolean
     */
    @Override
    public boolean addOauthClient(SysOauthClient oauthClient) {
        return save(oauthClient);
    }

    /**
     * 通过id修改oauth终端
     *
     * @param oauthClient oauth终端信息
     * @return boolean
     */
    @Override
    public boolean updateOauthClient(SysOauthClient oauthClient) {
        return updateById(oauthClient);
    }

    /**
     * 通过id删除oauth终端
     *
     * @param oauthClientIds oauthClientIds
     * @return boolean
     */
    @Override
    @Transactional
    public boolean removeOauthClientByIds(List<Long> oauthClientIds) {
        // List<String> oauthClientIdList = U.getIdListByStringWithEmptyThrow(oauthClientIds, "oauth终端Id");
        return removeByIds(oauthClientIds);
    }

    /**
     * 列表查询oauth终端
     *
     * @param oauthClientQuery 查询对象
     * @return 列表
     */
    @Override
    public List<SysOauthClient> listOauthClient(OauthClientQuery oauthClientQuery) {
        return queryChain().list();
    }

    /**
     * 分页查询oauth终端
     *
     * @param page             分页对象
     * @param oauthClientQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<SysOauthClient> pageOauthClient(Page<SysOauthClient> page, OauthClientQuery oauthClientQuery) {
        return queryChain().page(page);
    }

}