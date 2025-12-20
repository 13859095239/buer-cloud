package com.buer.system.biz.service.impl;

import com.buer.system.api.entity.SysOauthToken;
import com.buer.system.api.query.OauthTokenQuery;
import com.buer.system.biz.service.SysOauthTokenService;
import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 令牌 ServiceImpl
 *
 * @author zoulan
 * @since 2024-09-07
 */
@Service
@RequiredArgsConstructor
public class SysOauthTokenServiceImpl implements SysOauthTokenService {

    /**
     * 通过id删除令牌
     *
     * @param oauthTokenIds oauthTokenIds
     * @return boolean
     */
    @Override
    public boolean removeOauthTokenByIds(List<Long> oauthTokenIds) {
        return false;
        // return removeById(id);
    }

    /**
     * 列表查询令牌
     *
     * @param oauthTokenQuery 查询对象
     * @return 列表
     */
    @Override
    public List<SysOauthToken> listOauthToken(OauthTokenQuery oauthTokenQuery) {
        return null;
        // return mapper.listOauthTokens(oauthTokenQuery);
    }

    /**
     * 分页查询令牌
     *
     * @param page            分页对象
     * @param oauthTokenQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<SysOauthToken> pageOauthToken(Page<?> page, OauthTokenQuery oauthTokenQuery) {
        return null;
        //        String listKey = "BuerAuthorization:oauth2:access-token:";
        //        RList<String> a = RedisUtils.keys(listKey);
        //        long currentPage = page.getCurrent();
        //        long pageSize = page.getSize();
        //        long start = (currentPage - 1) * pageSize;
        //        // long end = Math.min(start + pageSize - 1, sampleList.size() - 1);
        //        // 获取分页数据
        //        // List<String> paginatedData = sampleList.range(start, end);
        //        return mapper.getOauthTokenPage(page, oauthTokenQuery);
    }

}