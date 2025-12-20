package com.buer.system.biz.service;

import com.buer.system.api.entity.SysOauthToken;
import com.buer.system.api.query.OauthTokenQuery;
import com.mybatisflex.core.paginate.Page;

import java.util.List;

/**
 * 令牌 Service
 *
 * @author zoulan
 * @since 2024-09-07
 */
public interface SysOauthTokenService {

    /**
     * 通过id删除令牌
     *
     * @param oauthTokenIds oauthTokenIds
     * @return boolean
     */
    boolean removeOauthTokenByIds(List<Long> oauthTokenIds);

    /**
     * 列表查询令牌
     *
     * @param oauthTokenQuery 查询对象
     * @return 列表
     */
    List<SysOauthToken> listOauthToken(OauthTokenQuery oauthTokenQuery);

    /**
     * 分页查询令牌
     *
     * @param page            分页对象
     * @param oauthTokenQuery 查询对象
     * @return 分页对象
     */
    Page<SysOauthToken> pageOauthToken(Page<?> page, OauthTokenQuery oauthTokenQuery);

}