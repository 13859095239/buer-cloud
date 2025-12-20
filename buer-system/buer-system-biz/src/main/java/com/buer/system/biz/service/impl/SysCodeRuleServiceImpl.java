package com.buer.system.biz.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.buer.common.core.exception.CheckException;
import com.buer.common.redis.util.RedisUtils;
import com.buer.system.api.entity.SysCodeRule;
import com.buer.system.api.query.CodeRuleQuery;
import com.buer.system.api.vo.CodeRuleVO;
import com.buer.system.biz.mapper.SysCodeRuleMapper;
import com.buer.system.biz.service.SysCodeRuleService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.buer.system.api.entity.table.SysCodeRuleTableDef.SYS_CODE_RULE;

/**
 * 编号规则 ServiceImpl
 *
 * @author zoulan
 * @since 2024-06-07
 */
@Service
@RequiredArgsConstructor
public class SysCodeRuleServiceImpl extends ServiceImpl<SysCodeRuleMapper, SysCodeRule> implements SysCodeRuleService {
    private static final String LOCK_KEY = "codeRuleLock";

    /**
     * 通过id查询编号规则
     *
     * @param id id
     * @return CodeRuleVO
     */
    @Override
    public CodeRuleVO getCodeRuleById(Long id) {
        return queryChain().and(SYS_CODE_RULE.ID.eq(id))
            .oneAs(CodeRuleVO.class);
    }

    /**
     * 通过code查询编号规则
     *
     * @param code code
     * @return 编号规则信息
     */
    @Override
    public CodeRuleVO getCodeRuleByCode(String code) {
        return queryChain().and(SYS_CODE_RULE.CODE.eq(code))
            .oneAs(CodeRuleVO.class);
    }

    /**
     * 新增编号规则
     *
     * @param codeRule 编号规则对象
     * @return boolean
     */
    @Override
    public boolean addCodeRule(SysCodeRule codeRule) {
        return save(codeRule);
    }

    /**
     * 通过id修改编号规则
     *
     * @param codeRule 编号规则对象
     * @return boolean
     */
    @Override
    public boolean updateCodeRule(SysCodeRule codeRule) {
        return updateById(codeRule);
    }

    /**
     * 通过id删除编号规则
     *
     * @param codeRuleIds codeRuleIds
     * @return boolean
     */
    @Override
    @Transactional
    public boolean removeCodeRuleByIds(List<Long> codeRuleIds) {
        //  List<String> idList = U.getIdListByStringWithEmptyThrow(codeRuleIds, "编号规则ID");
        return removeByIds(codeRuleIds);
    }

    /**
     * 列表查询编号规则
     *
     * @param codeRuleQuery 查询对象
     * @return 列表
     */
    @Override
    public List<CodeRuleVO> listCodeRule(CodeRuleQuery codeRuleQuery) {
        return queryChain()
            .listAs(CodeRuleVO.class);
    }

    /**
     * 分页查询编号规则
     *
     * @param page          分页对象
     * @param codeRuleQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<CodeRuleVO> pageCodeRule(Page<CodeRuleVO> page, CodeRuleQuery codeRuleQuery) {
        return queryChain()
            .and(SYS_CODE_RULE.CODE.like(codeRuleQuery.getCode()))
            .and(SYS_CODE_RULE.NAME.like(codeRuleQuery.getName()))
            .pageAs(page, CodeRuleVO.class);
    }

    /**
     * 通过code获取最新的编号
     *
     * @param code 编号规则code
     * @return 编号
     */
    @Override
    public String generateCodeByKey(String code) {
        return generateCode(null, code, "code");
    }

    /**
     * 通过id获取最新的编号
     *
     * @param id 编号规则id
     * @return 编号
     */
    @Override
    public String generateCodeById(Long id) {
        return generateCode(id, null, "id");
    }

    /**
     * 获取最新的编号
     *
     * @param id   编号规则id
     * @param code 编号规则code
     * @param type id | code
     * @return 编号
     */
    private String generateCode(Long id, String code, String type) {
        RedissonClient redissonClient = RedisUtils.getClient();
        RLock lock = redissonClient.getLock(LOCK_KEY);
        try {
            CodeRuleVO codeRuleVO;
            if ("id".equals(type)) {
                codeRuleVO = getCodeRuleById(id);
            } else {
                codeRuleVO = getCodeRuleByCode(code);
            }
            // 分布式事务锁
            lock.lock();
            if (codeRuleVO == null) {
                throw new CheckException("保存失败，编号规则不存在");
            }
            // 当前编号
            Integer currentNumber = codeRuleVO.getCurrentNumber();
            if (currentNumber == null) {
                currentNumber = 0;
            }
            // 当前编号递增
            currentNumber++;
            String prefix = codeRuleVO.getPrefix();
            String codeRuleDateType = codeRuleVO.getCodeRuleDateType();
            DateUtil.format(new Date(), codeRuleDateType);
            String dateString = "";
            Integer digit = codeRuleVO.getDigit();
            String currentNumberPad = StrUtil.padPre(currentNumber.toString(), digit, "-");
            String currentCode = StrUtil.format("{}{}{}", prefix, dateString, currentNumberPad);
            updateCodeRule(new SysCodeRule()
                .setId(codeRuleVO.getId())
                .setCurrentNumber(currentNumber)
                .setCurrentOutput(currentCode)
            );
            return currentCode;
        } finally {
            lock.unlock();
        }

    }
}