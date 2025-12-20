package com.buer.flow.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.buer.flow.api.dto.CcDTO;
import com.buer.flow.api.entity.ActExtCc;
import com.buer.flow.api.query.CcQuery;
import com.buer.flow.api.vo.CcVO;
import com.buer.flow.biz.mapper.ActExtCcMapper;
import com.buer.flow.biz.service.ActExtCcService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.buer.flow.api.entity.table.ActExtCcTableDef.ACT_EXT_CC;
import static com.buer.flow.api.entity.table.ActExtDelegateTableDef.ACT_EXT_DELEGATE;

/**
 * 流程抄送 ServiceImpl
 *
 * @author zoulan
 * @since 2024-06-13
 */
@Service
@RequiredArgsConstructor
public class ActExtCcServiceImpl extends ServiceImpl<ActExtCcMapper, ActExtCc> implements ActExtCcService {

    /**
     * 通过id查询流程抄送
     *
     * @param id id
     * @return CcVO
     */
    @Override
    public CcVO getCcById(Long id) {
        return getQueryChain().and(ACT_EXT_CC.ID.eq(id))
            .oneAs(CcVO.class);
    }

    /**
     * 新增流程抄送
     *
     * @param CcDTO 流程抄送信息
     * @return boolean
     */
    @Override
    public boolean addCc(CcDTO CcDTO) {
        ActExtCc model = new ActExtCc();
        BeanUtil.copyProperties(CcDTO, model);
        return save(model);
    }

    /**
     * 通过id修改流程抄送
     *
     * @param CcDTO 流程抄送信息
     * @return boolean
     */
    @Override
    public boolean updateCc(CcDTO CcDTO) {
        ActExtCc model = new ActExtCc();
        BeanUtil.copyProperties(CcDTO, model);
        return updateById(model);
    }

    /**
     * 通过id删除流程抄送
     *
     * @param ccIds ids
     * @return boolean
     */
    @Override
    @Transactional
    public boolean removeCcByIds(List<Long> ccIds) {
        return removeByIds(ccIds);
    }

    /**
     * 列表查询流程抄送
     *
     * @param ccQuery 查询对象
     * @return 列表
     */
    @Override
    public List<CcVO> listCc(CcQuery ccQuery) {
        return getQueryChainByQuery(ccQuery).listAs(CcVO.class);
    }

    /**
     * 分页查询我的待办流程抄送
     *
     * @param page    分页对象
     * @param ccQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<CcVO> pageDoCc(Page<CcVO> page, CcQuery ccQuery) {
        return getQueryChainByQuery(ccQuery).pageAs(page, CcVO.class);
    }

    /**
     * 分页查询我的已办流程抄送
     *
     * @param page    分页对象
     * @param ccQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<CcVO> pageDoneCc(Page<CcVO> page, CcQuery ccQuery) {
        return getQueryChainByQuery(ccQuery).pageAs(page, CcVO.class);
    }

    /**
     * 获取QueryChain对象
     */
    QueryChain<ActExtCc> getQueryChain() {
        return queryChain().select(ACT_EXT_DELEGATE.ALL_COLUMNS);
    }

    /**
     * 通过delegateQuery获取QueryChain对象
     */
    private QueryChain<ActExtCc> getQueryChainByQuery(CcQuery entity) {
        return getQueryChain()
            .and(ACT_EXT_CC.ID.in(entity.getIdList()))
            .orderBy(ACT_EXT_CC.CREATE_TIME.desc());
    }
}