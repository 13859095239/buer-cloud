package com.buer.flow.biz.service;

import com.buer.flow.api.dto.CcDTO;
import com.buer.flow.api.entity.ActExtCc;
import com.buer.flow.api.query.CcQuery;
import com.buer.flow.api.vo.CcVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 流程抄送 Service
 *
 * @author zoulan
 * @since 2024-06-13
 */
public interface ActExtCcService extends IService<ActExtCc> {

    /**
     * 通过id查询流程抄送
     *
     * @param id id
     * @return 流程抄送信息
     */
    CcVO getCcById(Long id);

    /**
     * 新增流程抄送
     *
     * @param CcDTO 流程抄送信息
     * @return boolean
     */
    boolean addCc(CcDTO CcDTO);

    /**
     * 通过id修改流程抄送
     *
     * @param CcDTO 流程抄送信息
     * @return boolean
     */
    boolean updateCc(CcDTO CcDTO);

    /**
     * 通过id删除流程抄送
     *
     * @param ccIds ids
     * @return boolean
     */
    boolean removeCcByIds(List<Long> ccIds);

    /**
     * 列表查询流程抄送
     *
     * @param ccQuery 查询对象
     * @return 列表
     */
    List<CcVO> listCc(CcQuery ccQuery);

    /**
     * 分页查询我的待办流程抄送
     *
     * @param page    分页对象
     * @param ccQuery 查询对象
     * @return 分页对象
     */
    Page<CcVO> pageDoCc(Page<CcVO> page, CcQuery ccQuery);

    /**
     * 分页查询我的已办流程抄送
     *
     * @param page    分页对象
     * @param ccQuery 查询对象
     * @return 分页对象
     */
    Page<CcVO> pageDoneCc(Page<CcVO> page, CcQuery ccQuery);
}