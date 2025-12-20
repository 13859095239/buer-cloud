package com.buer.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.buer.common.core.entity.R;
import com.buer.common.security.util.SecurityUtils;
import com.buer.flow.api.dto.CompleteTaskDTO;
import com.buer.flow.api.dto.CreateProcinstDTO;
import com.buer.flow.api.dto.ListenerDTO;
import com.buer.flow.api.feign.RemoteActHiProcinstService;
import com.buer.flow.api.feign.RemoteActRuTaskService;
import com.buer.flow.api.vo.CreatedProcinstVO;
import com.buer.system.api.dto.LeaveDTO;
import com.buer.system.api.entity.SysLeave;
import com.buer.system.api.filler.UserLabelFiller;
import com.buer.system.api.query.LeaveQuery;
import com.buer.system.api.vo.LeaveVO;
import com.buer.system.biz.mapper.SysLeaveMapper;
import com.buer.system.biz.service.SysLeaveService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.buer.system.api.entity.table.SysLeaveTableDef.SYS_LEAVE;

/**
 * 请假 ServiceImpl
 *
 * @author zoulan
 * @since 2024-06-07
 */
@Service
@RequiredArgsConstructor
public class SysLeaveServiceImpl extends ServiceImpl<SysLeaveMapper, SysLeave> implements SysLeaveService {

    private final RemoteActHiProcinstService remoteActHiProcinstService;
    private final RemoteActRuTaskService remoteActRuTaskService;
    private final UserLabelFiller userLabelFiller;

    /**
     * 通过id查询请假
     *
     * @param id id
     * @return LeaveVO
     */
    @Override
    public LeaveVO getLeaveById(Long id) {
        return queryChain().and(SYS_LEAVE.ID.eq(id))
            .oneAs(LeaveVO.class);
    }

    /**
     * 通过流程实例id查询请假
     *
     * @param procInstId 流程实例id
     * @return LeaveVO
     */
    @Override
    public LeaveVO getLeaveByProcInstId(String procInstId) {
        return queryChain().and(SYS_LEAVE.PROC_INST_ID.eq(procInstId))
            .oneAs(LeaveVO.class);
    }

    /**
     * 新增请假
     *
     * @param leaveDTO 请假信息
     * @return boolean
     */
    @Override
    public boolean addLeave(LeaveDTO<CreateProcinstDTO> leaveDTO) {
        SysLeave model = new SysLeave();
        BeanUtil.copyProperties(leaveDTO, model);
        // 创建流程实例
        CreateProcinstDTO createProcinstDTO = leaveDTO.getFlowData();
        R<CreatedProcinstVO> remoteResult = remoteActHiProcinstService.createProcinst(createProcinstDTO);
        CreatedProcinstVO createdProcinstVO = remoteResult.getData();
        // 流程数据赋值给业务
        model.setProcStartUserId(SecurityUtils.getUserId());
        BeanUtil.copyProperties(createdProcinstVO, model);
        // 创建请假数据
        save(model);
        return true;
    }

    /**
     * 通过id修改请假
     *
     * @param leaveDTO 请假信息
     * @return boolean
     */
    @Override
    public boolean updateLeave(LeaveDTO leaveDTO) {
        SysLeave model = new SysLeave();
        BeanUtil.copyProperties(leaveDTO, model);
        return updateById(model);
    }

    /**
     * 通过id删除请假
     *
     * @param leaveIds leaveIds
     * @return boolean
     */
    @Override
    @Transactional
    public boolean removeLeaveByIds(List<Long> leaveIds) {
        List<String> procinstIds = queryChain().select(SYS_LEAVE.PROC_INST_ID)
            .where(SYS_LEAVE.ID.in(leaveIds))
            .listAs(String.class);
        // 删除了流程实例
        remoteActHiProcinstService.removeProcinstByIds(procinstIds);
        return removeByIds(leaveIds);
    }

    /**
     * 列表查询请假
     *
     * @param leaveQuery 查询对象
     * @return 列表
     */
    @Override
    public List<LeaveVO> listLeave(LeaveQuery leaveQuery) {
        return queryChain().listAs(LeaveVO.class);
    }

    /**
     * 分页查询请假
     *
     * @param page       分页对象
     * @param leaveQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<LeaveVO> pageLeave(Page<LeaveVO> page, LeaveQuery leaveQuery) {
        Page<LeaveVO> dataPage = queryChain().pageAs(page, LeaveVO.class);
        fillLeave(dataPage.getRecords());
        return dataPage;
    }

    @Override
    public Boolean completeLeave(LeaveDTO<CompleteTaskDTO> leaveDTO) {
        // 更新请假数据
        SysLeave model = new SysLeave();
        BeanUtil.copyProperties(leaveDTO, model);
        updateById(model);
        // 完成流程任务
        CompleteTaskDTO completeTaskDTO = leaveDTO.getFlowData();
        remoteActRuTaskService.completeTask(completeTaskDTO);
        return true;
    }

    /**
     * 流程事件通知
     *
     * @param listenerDTO 流程监听事件DTO
     * @return Boolean
     */
    @Override
    public Boolean flowListener(ListenerDTO listenerDTO) {
        return null;
    }

    /**
     * 填充外键信息
     */
    private void fillLeave(List<LeaveVO> leaveVOList) {
        userLabelFiller.fillField(leaveVOList,
            LeaveVO::getLeaveUserId,
            LeaveVO::setLeaveUserName);
    }
}