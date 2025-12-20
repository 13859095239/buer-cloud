package com.buer.flow.biz.service;

import com.buer.flow.api.dto.CreateProcinstDTO;
import com.buer.flow.api.entity.ActHiProcinst;
import com.buer.flow.api.query.ProcinstQuery;
import com.buer.flow.api.vo.CreateProcinstInfoVO;
import com.buer.flow.api.vo.CreatedProcinstVO;
import com.buer.flow.api.vo.ProcinstInfoVO;
import com.buer.flow.api.vo.ProcinstVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 流程实例 Service
 *
 * @author zoulan
 * @since 2023-05-11
 */
public interface ActHiProcinstService extends IService<ActHiProcinst> {

    /**
     * 通过id查询流程实例
     *
     * @param id id
     * @return 实例信息
     */
    ProcinstVO getProcinstById(String id);

    /**
     * 通过模型key获取新建流程实例信息
     *
     * @param modelKey 模型key
     * @return 流程实例信息 VO
     */
    CreateProcinstInfoVO getCreateProcinstInfo(String modelKey);

    /**
     * 通过流程实例ID获取流程信息
     *
     * @param procinstId 流程实例id
     * @return 流程实例信息 VO
     */
    ProcinstInfoVO getProcinstInfoById(String procinstId);

    /**
     * 通过id修改流程实例
     *
     * @param procinst 流程实例对象
     * @return Boolean
     */
    boolean updateProcinst(ActHiProcinst procinst);

    /**
     * 通过id删除流程实例
     *
     * @param procinstIds procinstIds
     * @return Boolean
     */
    boolean removeProcinstByIds(List<String> procinstIds);

    /**
     * 列表查询流程实例
     *
     * @param procinstQuery 查询对象
     * @return 列表
     */
    List<ProcinstVO> listProcinst(ProcinstQuery procinstQuery);

    /**
     * 分页查询流程实例
     *
     * @param page          分页对象
     * @param procinstQuery 查询对象
     * @return 分页对象
     */
    Page<ProcinstVO> pageProcinst(Page<ProcinstVO> page, ProcinstQuery procinstQuery);

    /**
     * 创建流程流程实例
     *
     * @param createProcinstDTO 创建流程对象
     * @return CreatedProcinstVO
     */
    CreatedProcinstVO createProcinst(CreateProcinstDTO createProcinstDTO);

    /**
     * 激活流程实例
     *
     * @param procInstId 流程实例id
     * @return Boolean
     */
    Boolean activateProcinstById(String procInstId);

    /**
     * 挂起流程实例
     *
     * @param procInstId 流程实例id
     * @return Boolean
     */
    Boolean suspendProcinstById(String procInstId);

}