package com.buer.flow.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.buer.flow.api.dto.DelegateDTO;
import com.buer.flow.api.entity.ActExtDelegate;
import com.buer.flow.api.query.DelegateQuery;
import com.buer.flow.api.vo.DelegateVO;
import com.buer.flow.biz.mapper.ActExtDelegateMapper;
import com.buer.flow.biz.service.ActExtDelegateService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.buer.flow.api.entity.table.ActExtDelegateTableDef.ACT_EXT_DELEGATE;

/**
 * 流程委托 ServiceImpl
 *
 * @author zoulan
 * @since 2024-06-13
 */
@Service
@RequiredArgsConstructor
public class ActExtDelegateServiceImpl extends ServiceImpl<ActExtDelegateMapper, ActExtDelegate> implements ActExtDelegateService {

    /**
     * 通过id查询流程委托
     *
     * @param id id
     * @return DelegateVO
     */
    @Override
    public DelegateVO getDelegateById(Long id) {
        return getQueryChain().and(ACT_EXT_DELEGATE.ID.eq(id))
            .oneAs(DelegateVO.class);
    }

    /**
     * 新增流程委托
     *
     * @param delegateDTO 流程委托信息
     * @return boolean
     */
    @Override
    public boolean saveDelegate(DelegateDTO delegateDTO) {
        ActExtDelegate model = new ActExtDelegate();
        BeanUtil.copyProperties(delegateDTO, model);
        return save(model);
    }

    /**
     * 通过id修改流程委托
     *
     * @param delegateDTO 流程委托信息
     * @return boolean
     */
    @Override
    public boolean updateDelegate(DelegateDTO delegateDTO) {
        ActExtDelegate model = new ActExtDelegate();
        BeanUtil.copyProperties(delegateDTO, model);
        return updateById(model);
    }

    /**
     * 通过id删除流程委托
     *
     * @param delegateIds id
     * @return boolean
     */
    @Override
    public boolean removeDelegateByIds(List<Long> delegateIds) {
        return removeByIds(delegateIds);
    }

    /**
     * 列表查询流程委托
     *
     * @param delegateQuery 查询对象
     * @return 列表
     */
    @Override
    public List<DelegateVO> listDelegate(DelegateQuery delegateQuery) {
        return getQueryChainByQuery(delegateQuery).listAs(DelegateVO.class);
    }

    /**
     * 分页查询流程委托
     *
     * @param page          分页对象
     * @param delegateQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<DelegateVO> pageDelegate(Page<DelegateVO> page, DelegateQuery delegateQuery) {
        return getQueryChainByQuery(delegateQuery).pageAs(page, DelegateVO.class);
    }

    /**
     * 获取QueryChain对象
     */
    QueryChain<ActExtDelegate> getQueryChain() {
        return queryChain().select(ACT_EXT_DELEGATE.ALL_COLUMNS);
    }

    /**
     * 通过delegateQuery获取QueryChain对象
     */
    private QueryChain<ActExtDelegate> getQueryChainByQuery(DelegateQuery entity) {
        return getQueryChain()
            .and(ACT_EXT_DELEGATE.ID.in(entity.getIdList()))
            .orderBy(ACT_EXT_DELEGATE.CREATE_TIME.desc());
    }
}