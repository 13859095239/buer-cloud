package com.buer.flow.biz.service;

import com.buer.flow.api.entity.ActHiTaskinst;
import com.buer.flow.api.query.TaskinstForMyQuery;
import com.buer.flow.api.query.TaskinstQuery;
import com.buer.flow.api.vo.DoneTaskinstInfoVO;
import com.buer.flow.api.vo.TaskinstVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 历史任务 Service
 *
 * @author zoulan
 * @since 2023-05-11
 */
public interface ActHiTaskinstService extends IService<ActHiTaskinst> {

    /**
     * 通过id查询历史任务
     *
     * @param id id
     * @return 历史任务信息
     */
    TaskinstVO getTaskinstById(String id);

    /**
     * 通过任务ID获取我的已办任务信息
     *
     * @param taskId 任务id
     * @return 已办任务信息 VO
     */
    DoneTaskinstInfoVO getMyDoneTaskinstInfo(String taskId);

    /**
     * 新增历史任务
     *
     * @param taskinst 历史任务对象
     * @return Boolean
     */
    boolean addTaskinst(ActHiTaskinst taskinst);

    /**
     * 通过id修改历史任务
     *
     * @param taskinst 历史任务对象
     * @return Boolean
     */
    boolean updateTaskinstById(ActHiTaskinst taskinst);

    /**
     * 列表查询历史任务
     *
     * @param taskinstQuery 查询对象
     * @return 列表
     */
    List<TaskinstVO> listTaskinst(TaskinstQuery taskinstQuery);

    /**
     * 分页查询历史任务
     *
     * @param page          分页对象
     * @param taskinstQuery 查询对象
     * @return 分页对象
     */
    Page<TaskinstVO> pageTaskinst(Page<TaskinstVO> page, TaskinstQuery taskinstQuery);

    /**
     * 分页查询历史任务
     *
     * @param page               分页对象
     * @param taskinstForMyQuery 查询对象
     * @return 分页对象
     */
    Page<TaskinstVO> pageMyDoneTaskinst(Page<TaskinstVO> page, TaskinstForMyQuery taskinstForMyQuery);

}