package com.buer.system.biz.service;

import com.buer.system.api.entity.SysCodeRule;
import com.buer.system.api.query.CodeRuleQuery;
import com.buer.system.api.vo.CodeRuleVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 编号规则 Service
 *
 * @author zoulan
 * @since 2024-06-07
 */
public interface SysCodeRuleService extends IService<SysCodeRule> {

    /**
     * 通过id查询编号规则
     *
     * @param id id
     * @return 编号规则信息
     */
    CodeRuleVO getCodeRuleById(Long id);

    /**
     * 通过code查询编号规则
     *
     * @param code code
     * @return 编号规则信息
     */
    CodeRuleVO getCodeRuleByCode(String code);

    /**
     * 新增编号规则
     *
     * @param codeRule 编号规则对象
     * @return boolean
     */
    boolean addCodeRule(SysCodeRule codeRule);

    /**
     * 通过id修改编号规则
     *
     * @param codeRule 编号规则对象
     * @return boolean
     */
    boolean updateCodeRule(SysCodeRule codeRule);

    /**
     * 通过id删除编号规则
     *
     * @param codeRuleIds codeRuleIds
     * @return boolean
     */
    boolean removeCodeRuleByIds(List<Long> codeRuleIds);

    /**
     * 列表查询编号规则
     *
     * @param codeRuleQuery 查询对象
     * @return 列表
     */
    List<CodeRuleVO> listCodeRule(CodeRuleQuery codeRuleQuery);

    /**
     * 分页查询编号规则
     *
     * @param page          分页对象
     * @param codeRuleQuery 查询对象
     * @return 分页对象
     */
    Page<CodeRuleVO> pageCodeRule(Page<CodeRuleVO> page, CodeRuleQuery codeRuleQuery);

    /**
     * 通过key获取最新的编号
     *
     * @param key 编号规则key
     * @return 编号
     */
    String generateCodeByKey(String key);

    /**
     * 通过id获取最新的编号
     *
     * @param id 编号规则id
     * @return 编号
     */
    String generateCodeById(Long id);
}