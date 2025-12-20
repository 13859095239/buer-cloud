package com.buer.system.biz.service;

import com.buer.flow.api.dto.CompleteTaskDTO;
import com.buer.flow.api.dto.CreateProcinstDTO;
import com.buer.flow.api.dto.ListenerDTO;
import com.buer.system.api.dto.LeaveDTO;
import com.buer.system.api.entity.SysLeave;
import com.buer.system.api.query.LeaveQuery;
import com.buer.system.api.vo.LeaveVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 请假 Service
 *
 * @author zoulan
 * @since 2024-06-07
 */
public interface SysLeaveService extends IService<SysLeave> {

    /**
     * 通过id查询请假
     *
     * @param id id
     * @return 请假信息
     */
    LeaveVO getLeaveById(Long id);

    /**
     * 通过流程实例id查询请假
     *
     * @param procInstId 流程实例id
     * @return LeaveVO
     */
    LeaveVO getLeaveByProcInstId(String procInstId);

    /**
     * 新增请假
     *
     * @param leaveDTO 请假信息
     * @return boolean
     */
    boolean addLeave(LeaveDTO<CreateProcinstDTO> leaveDTO);

    /**
     * 通过id修改请假
     *
     * @param leaveDTO 请假信息
     * @return boolean
     */
    boolean updateLeave(LeaveDTO<CreateProcinstDTO> leaveDTO);

    /**
     * 通过id删除请假
     *
     * @param leaveIds leaveIds
     * @return boolean
     */
    boolean removeLeaveByIds(List<Long> leaveIds);

    /**
     * 列表查询请假
     *
     * @param leaveQuery 查询对象
     * @return 列表
     */
    List<LeaveVO> listLeave(LeaveQuery leaveQuery);

    /**
     * 分页查询请假
     *
     * @param page       分页对象
     * @param leaveQuery 查询对象
     * @return 分页对象
     */
    Page<LeaveVO> pageLeave(Page<LeaveVO> page, LeaveQuery leaveQuery);

    Boolean completeLeave(LeaveDTO<CompleteTaskDTO> leaveDTO);

    /**
     * 流程事件通知
     *
     * @param listenerDTO 流程监听事件DTO
     * @return Boolean
     */
    Boolean flowListener(ListenerDTO listenerDTO);
}