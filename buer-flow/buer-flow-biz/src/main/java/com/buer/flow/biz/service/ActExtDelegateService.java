package com.buer.flow.biz.service;

import com.buer.flow.api.dto.DelegateDTO;
import com.buer.flow.api.entity.ActExtDelegate;
import com.buer.flow.api.query.DelegateQuery;
import com.buer.flow.api.vo.DelegateVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 流程委托 Service
 *
 * @author zoulan
 * @since 2024-06-13
 */
public interface ActExtDelegateService extends IService<ActExtDelegate> {

    /**
     * 通过id查询流程委托
     *
     * @param id id
     * @return 流程委托信息
     */
    DelegateVO getDelegateById(Long id);

    /**
     * 新增流程委托
     *
     * @param delegateDTO 流程委托信息
     * @return boolean
     */
    boolean saveDelegate(DelegateDTO delegateDTO);

    /**
     * 通过id修改流程委托
     *
     * @param delegateDTO 流程委托信息
     * @return boolean
     */
    boolean updateDelegate(DelegateDTO delegateDTO);

    /**
     * 通过id删除流程委托
     *
     * @param delegateIds delegateIds
     * @return boolean
     */
    boolean removeDelegateByIds(List<Long> delegateIds);

    /**
     * 列表查询流程委托
     *
     * @param delegateQuery 查询对象
     * @return 列表
     */
    List<DelegateVO> listDelegate(DelegateQuery delegateQuery);

    /**
     * 分页查询流程委托
     *
     * @param page          分页对象
     * @param delegateQuery 查询对象
     * @return 分页对象
     */
    Page<DelegateVO> pageDelegate(Page<DelegateVO> page, DelegateQuery delegateQuery);

}