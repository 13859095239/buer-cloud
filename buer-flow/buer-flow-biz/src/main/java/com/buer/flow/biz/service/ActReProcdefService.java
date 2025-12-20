package com.buer.flow.biz.service;

import com.buer.flow.api.entity.ActReProcdef;
import com.buer.flow.api.query.ProcessDefinitionQuery;
import com.buer.flow.api.vo.ProcessDefinitionVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.repository.ProcessDefinition;

import java.util.List;

/**
 * 流程定义 Service
 *
 * @author zoulan
 * @since 2024-06-09
 */
public interface ActReProcdefService extends IService<ActReProcdef> {

    /**
     * 通过id查询流程定义
     *
     * @param id id
     * @return 流程定义信息
     */
    ProcessDefinitionVO getProcessDefinitionById(String id);

    /**
     * 新增流程定义
     *
     * @param processDefinition 流程定义信息
     * @return boolean
     */
    boolean addProcessDefinition(ActReProcdef processDefinition);

    /**
     * 通过id修改流程定义
     *
     * @param processDefinition 流程定义信息
     * @return boolean
     */
    boolean updateProcessDefinition(ActReProcdef processDefinition);

    /**
     * 通过id删除流程定义
     *
     * @param processDefinitionIds processDefinitionIds
     * @return boolean
     */
    boolean removeProcessDefinitionByIds(List<Long> processDefinitionIds);

    /**
     * 列表查询流程定义
     *
     * @param processDefinitionQuery 查询对象
     * @return 列表
     */
    List<ProcessDefinitionVO> listProcessDefinition(ProcessDefinitionQuery processDefinitionQuery);

    /**
     * 分页查询流程定义
     *
     * @param page                   分页对象
     * @param processDefinitionQuery 查询对象
     * @return 分页对象
     */
    Page<ProcessDefinitionVO> pageProcessDefinition(Page<?> page, ProcessDefinitionQuery processDefinitionQuery);

    /**
     * 获取指定流程定义的BpmnModel
     *
     * @param processDefinitionId 流程定义Id
     * @return 关联的BpmnModel。
     */
    BpmnModel getBpmnModelByDefinitionId(String processDefinitionId);

    /**
     * 获取最新流程定义的BpmnModel
     *
     * @param modelKey 模型key
     * @return 关联的BpmnModel
     */
    ProcessDefinition getNewestProcessDefinitionByModelKey(String modelKey);

    /**
     * 通过流程定义id获取流程图
     *
     * @param procDefId 流程定义Id。
     * @return 流程图
     */
    String getBpmnByProcDefId(String procDefId);

    /**
     * 通过模型key获取最新流程图
     *
     * @param modelKey 流程key
     * @return 流程图
     */
    String getNewestBpmnByModelKey(String modelKey);

    /**
     * 通过id更新流程定义状态
     * 激活->挂起
     *
     * @param id 流程定义id
     * @return Boolean
     */
    Boolean suspendProcDefById(String id);

    /**
     * 通过id更新流程定义状态
     * 挂起->激活
     *
     * @param id 流程定义id
     * @return Boolean
     */
    Boolean activateProcDefById(String id);

}