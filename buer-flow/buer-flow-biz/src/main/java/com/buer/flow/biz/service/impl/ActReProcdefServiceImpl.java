package com.buer.flow.biz.service.impl;

import com.buer.common.core.exception.CheckException;
import com.buer.flow.api.entity.ActReProcdef;
import com.buer.flow.api.query.ProcessDefinitionQuery;
import com.buer.flow.api.vo.ProcessDefinitionVO;
import com.buer.flow.biz.mapper.ActReProcdefMapper;
import com.buer.flow.biz.service.ActReProcdefService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 流程定义 ServiceImpl
 *
 * @author zoulan
 * @since 2024-06-09
 */
@Service
@RequiredArgsConstructor
public class ActReProcdefServiceImpl extends ServiceImpl<ActReProcdefMapper, ActReProcdef> implements ActReProcdefService {

    private final RepositoryService repositoryService;

    /**
     * 通过id查询流程定义
     *
     * @param id id
     * @return ProcessDefinitionVO
     */
    @Override
    public ProcessDefinitionVO getProcessDefinitionById(String id) {
        return mapper.getProcessDefinitionById(id);
    }

    /**
     * 新增流程定义
     *
     * @param processDefinition 流程定义信息
     * @return boolean
     */
    @Override
    public boolean addProcessDefinition(ActReProcdef processDefinition) {
        return save(processDefinition);
    }

    /**
     * 通过id修改流程定义
     *
     * @param processDefinition 流程定义信息
     * @return boolean
     */
    @Override
    public boolean updateProcessDefinition(ActReProcdef processDefinition) {
        return updateById(processDefinition);
    }

    /**
     * 通过id删除流程定义
     *
     * @param processDefinitionIds id
     * @return boolean
     */
    @Override
    public boolean removeProcessDefinitionByIds(List<Long> processDefinitionIds) {
        return removeByIds(processDefinitionIds);
    }

    /**
     * 列表查询流程定义
     *
     * @param processDefinitionQuery 查询对象
     * @return 列表
     */
    @Override
    public List<ProcessDefinitionVO> listProcessDefinition(ProcessDefinitionQuery processDefinitionQuery) {
        return mapper.listProcessDefinitions(processDefinitionQuery);
    }

    /**
     * 分页查询流程定义
     *
     * @param page                   分页对象
     * @param processDefinitionQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<ProcessDefinitionVO> pageProcessDefinition(Page<?> page, ProcessDefinitionQuery processDefinitionQuery) {
        return mapper.getProcessDefinitionPage(page, processDefinitionQuery);

    }

    /**
     * 获取指定流程定义的BpmnModel。
     *
     * @param processDefinitionId 流程定义Id。
     * @return 关联的BpmnModel。
     */
    @Override
    public BpmnModel getBpmnModelByDefinitionId(String processDefinitionId) {
        return repositoryService.getBpmnModel(processDefinitionId);
    }

    /**
     * 获取最新流程定义的BpmnModel
     *
     * @param modelKey 模型key。
     * @return 关联的BpmnModel。
     */
    @Override
    public ProcessDefinition getNewestProcessDefinitionByModelKey(String modelKey) {
        return repositoryService
            .createProcessDefinitionQuery()
            .processDefinitionKey(modelKey)
            .latestVersion()
            .singleResult();
    }

    /**
     * 通过流程定义id获取流程图
     *
     * @param procDefId 流程定义Id。
     * @return 流程图
     */
    @Override
    public String getBpmnByProcDefId(String procDefId) {
        BpmnModel bpmnModel = getBpmnModelByDefinitionId(procDefId);
        return converterBpmnToString(bpmnModel);
    }

    /**
     * 通过模型key获取最新流程图
     *
     * @param modelKey 流程key
     * @return 流程图
     */
    @Override
    public String getNewestBpmnByModelKey(String modelKey) {
        ProcessDefinition processDefinition = getNewestProcessDefinitionByModelKey(modelKey);
        String processDefinitionId = processDefinition.getId();
        BpmnModel bpmnModel = getBpmnModelByDefinitionId(processDefinitionId);
        return converterBpmnToString(bpmnModel);
    }

    /**
     * 通过id更新流程定义状态
     * 激活->挂起
     *
     * @param id 流程定义id
     * @return Boolean
     */
    @Override
    public Boolean suspendProcDefById(String id) {
        ActReProcdef actReProcdef = getById(id);
        if (actReProcdef.getSuspensionState() == 2) {
            throw new CheckException("流程已经处于激活状态");
        }
        repositoryService.suspendProcessDefinitionById(id);
        return true;
    }

    /**
     * 通过id更新流程定义状态
     * 挂起->激活
     *
     * @param id 流程定义id
     * @return Boolean
     */
    @Override
    public Boolean activateProcDefById(String id) {
        ActReProcdef actReProcdef = getById(id);
        if (actReProcdef.getSuspensionState() == 1) {
            throw new CheckException("流程已经处于激活状态");
        }
        repositoryService.activateProcessDefinitionById(id);
        return true;
    }

    /**
     * 将bpmnModel 模型xml转换撑字符串类型
     *
     * @param bpmnModel bpmnModel对象
     * @return 模型xml转换撑字符串类型
     */
    @SneakyThrows
    private String converterBpmnToString(BpmnModel bpmnModel) {
        BpmnXMLConverter converter = new BpmnXMLConverter();
        byte[] xmlBytes = converter.convertToXML(bpmnModel);
        InputStream in = new ByteArrayInputStream(xmlBytes);
        return StreamUtils.copyToString(in, StandardCharsets.UTF_8);
    }
}