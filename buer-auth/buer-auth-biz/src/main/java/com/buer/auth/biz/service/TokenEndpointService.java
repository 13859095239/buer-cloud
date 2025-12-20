package com.buer.auth.biz.service;

import com.buer.system.api.query.CodeRuleQuery;
import com.buer.system.api.vo.CodeRuleVO;
import com.mybatisflex.core.paginate.Page;

import java.util.List;

/**
 * 登录 Service
 *
 * @author zoulan
 * @since 2023-05-06
 */
public interface TokenEndpointService {

    /**
     * 通过 tokens 删除 Token 令牌
     *
     * @param tokens tokens 列表
     * @return boolean
     */
    boolean removeTokenEndpointByTokens(List<String> tokens);

    /**
     * 分页查询 Token 令牌
     *
     * @param page          分页对象
     * @param codeRuleQuery 查询对象
     * @return 分页对象
     */
    Page<CodeRuleVO> pageTokenEndpoints(Page<CodeRuleVO> page, CodeRuleQuery codeRuleQuery);

}
