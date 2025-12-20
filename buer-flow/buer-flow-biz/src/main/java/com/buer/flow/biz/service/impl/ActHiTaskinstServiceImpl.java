package com.buer.flow.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.buer.common.security.util.SecurityUtils;
import com.buer.flow.api.entity.ActHiTaskinst;
import com.buer.flow.api.query.TaskinstForMyQuery;
import com.buer.flow.api.query.TaskinstQuery;
import com.buer.flow.api.vo.DoneTaskinstInfoVO;
import com.buer.flow.api.vo.TaskVO;
import com.buer.flow.api.vo.TaskinstVO;
import com.buer.flow.biz.mapper.ActHiTaskinstMapper;
import com.buer.flow.biz.service.ActHiTaskinstService;
import com.buer.system.api.filler.UserLabelFiller;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.buer.flow.api.entity.table.ActHiProcinstTableDef.ACT_HI_PROCINST;
import static com.buer.flow.api.entity.table.ActHiTaskinstTableDef.ACT_HI_TASKINST;
import static com.buer.flow.api.entity.table.ActReModelTableDef.ACT_RE_MODEL;
import static com.buer.flow.api.entity.table.ActReProcdefTableDef.ACT_RE_PROCDEF;

/**
 * ServiceImpl
 *
 * @author zoulan
 * @since 2023-05-11
 */
@Service
@RequiredArgsConstructor
public class ActHiTaskinstServiceImpl extends ServiceImpl<ActHiTaskinstMapper, ActHiTaskinst> implements ActHiTaskinstService {

    private final UserLabelFiller userLabelFiller;

    /**
     * 通过id查询历史任务
     *
     * @param id id
     * @return TaskinstVO
     */
    @Override
    public TaskinstVO getTaskinstById(String id) {
        return getQueryChain().and(ACT_HI_TASKINST.ID.eq(id))
            .oneAs(TaskinstVO.class);
    }

    /**
     * 通过任务ID获取我的已办任务信息
     *
     * @param taskId 任务id
     * @return 已办任务信息 VO
     */
    @Override
    public DoneTaskinstInfoVO getMyDoneTaskinstInfo(String taskId) {
        TaskinstVO taskinstVO = getTaskinstById(taskId);
        DoneTaskinstInfoVO doneTaskinstInfoVO = new DoneTaskinstInfoVO();
        BeanUtil.copyProperties(taskinstVO, doneTaskinstInfoVO);
        doneTaskinstInfoVO.setTaskId(taskId);
        doneTaskinstInfoVO.setTaskName(taskinstVO.getName());
        return doneTaskinstInfoVO;
    }

    /**
     * 新增历史任务
     *
     * @param taskinst 历史任务对象
     * @return Boolean
     */
    @Override
    public boolean addTaskinst(ActHiTaskinst taskinst) {
        return save(taskinst);
    }

    /**
     * 通过id修改历史任务
     *
     * @param taskinst 历史任务对象
     * @return Boolean
     */
    @Override
    public boolean updateTaskinstById(ActHiTaskinst taskinst) {
        return updateById(taskinst);
    }

    /**
     * 列表查询历史任务
     *
     * @param taskinstQuery 查询对象
     * @return 列表
     */
    @Override
    public List<TaskinstVO> listTaskinst(TaskinstQuery taskinstQuery) {
        return getQueryChainByQuery(taskinstQuery).listAs(TaskinstVO.class);
    }

    /**
     * 分页查询历史任务
     *
     * @param page          分页对象
     * @param taskinstQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<TaskinstVO> pageTaskinst(Page<TaskinstVO> page, TaskinstQuery taskinstQuery) {
        return getQueryChainByQuery(taskinstQuery).pageAs(page, TaskinstVO.class);
    }

    @Override
    public Page<TaskinstVO> pageMyDoneTaskinst(Page<TaskinstVO> page, TaskinstForMyQuery taskinstForMyQuery) {
        TaskinstQuery taskinstQuery = new TaskinstQuery()
            .setAssignee(SecurityUtils.getUserId()
                .toString());
        Page<TaskinstVO> dataPage = getQueryChainByQuery(taskinstQuery).pageAs(page, TaskinstVO.class);
        fillTaskinst(dataPage.getRecords());
        return dataPage;
    }

    /**
     * 获取QueryChain对象
     */
    QueryChain<ActHiTaskinst> getQueryChain() {
        return queryChain().select(ACT_HI_TASKINST.ALL_COLUMNS,
                ACT_HI_PROCINST.BUSINESS_KEY.as(TaskinstVO::getBusinessKey),
                ACT_HI_PROCINST.NAME.as(TaskinstVO::getProcInstName),
                ACT_HI_PROCINST.START_USER_ID.as(TaskVO::getStartUserId),
                ACT_RE_MODEL.KEY.as(TaskinstVO::getModelKey),
                ACT_RE_MODEL.NAME.as(TaskinstVO::getModelName),
                ACT_RE_MODEL.WEB_PATH.as(TaskinstVO::getModelWebPath)
            )
            .leftJoin(ACT_HI_PROCINST)
            .on(ACT_HI_PROCINST.PROC_INST_ID.eq(ACT_HI_TASKINST.PROC_INST_ID))
            .leftJoin(ACT_RE_PROCDEF)
            .on(ACT_RE_PROCDEF.ID.eq(ACT_HI_TASKINST.PROC_DEF_ID))
            .leftJoin(ACT_RE_MODEL)
            .on(ACT_RE_MODEL.KEY.eq(ACT_RE_PROCDEF.KEY));
    }

    /**
     * 通过userQuery获取QueryChain对象
     */
    private QueryChain<ActHiTaskinst> getQueryChainByQuery(TaskinstQuery taskinstQuery) {
        return getQueryChain()
            .and(ACT_HI_TASKINST.ASSIGNEE.eq(taskinstQuery.getAssignee()))
            .and(ACT_HI_TASKINST.NAME.like(taskinstQuery.getTaskName()))
            .and(ACT_HI_PROCINST.BUSINESS_KEY.like(taskinstQuery.getBusinessKey()))
            .and(ACT_HI_PROCINST.PROC_INST_ID.eq(taskinstQuery.getProcInstId()))
            .orderBy(ACT_HI_TASKINST.END_TIME.asc());
    }

    /**
     * 填充外键信息
     *
     * @param taskinstVOList 列表
     */
    private void fillTaskinst(List<TaskinstVO> taskinstVOList) {
        userLabelFiller.fillField(taskinstVOList,
            TaskinstVO::getStartUserId,
            TaskinstVO::setStartUserName);
    }
}