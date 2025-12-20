package com.buer.flow.biz.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.buer.common.core.exception.CheckException;
import com.buer.flow.api.dto.EditorXmlDTO;
import com.buer.flow.api.dto.ModelAddDTO;
import com.buer.flow.api.dto.ModelUpdateDTO;
import com.buer.flow.api.entity.ActReModel;
import com.buer.flow.api.query.ModelQuery;
import com.buer.flow.api.vo.ModelVO;
import com.buer.flow.api.vo.ProcessDefinitionVO;
import com.buer.flow.biz.mapper.ActReModelMapper;
import com.buer.flow.biz.service.ActReModelService;
import com.buer.flow.biz.service.ActReProcdefService;
import com.buer.flow.biz.utils.FlowSecurityUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.util.io.StringStreamSource;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

import static com.buer.flow.api.entity.table.ActExtCategoryTableDef.ACT_EXT_CATEGORY;
import static com.buer.flow.api.entity.table.ActReModelTableDef.ACT_RE_MODEL;

/**
 * 流程定义 ServiceImpl
 *
 * @author zoulan
 * @since 2023-05-11
 */
@Service
@RequiredArgsConstructor
public class ActReModelServiceImpl extends ServiceImpl<ActReModelMapper, ActReModel> implements ActReModelService {
    private static final Logger logger = LoggerFactory.getLogger(ActReModelServiceImpl.class);
    private static final String BPMN20_XML = ".bpmn20.xml";
    private final RepositoryService repositoryService;
    private final ActReProcdefService actReProcdefService;

    /**
     * 通过id查询模型
     *
     * @param id id
     * @return ModelVO
     */
    @Override
    public ModelVO getModelById(String id) {
        return getQueryChain().and(ACT_RE_MODEL.ID.eq(id))
            .oneAs(ModelVO.class);
    }

    /**
     * 通过模型key 查询模型
     *
     * @param key 模型key
     * @return 模型信息
     */
    @Override
    public ModelVO getModelByKey(String key) {
        return getQueryChain().and(ACT_RE_MODEL.KEY.eq(key))
            .oneAs(ModelVO.class);
    }

    /**
     * 新增模型
     *
     * @param modelAddDTO 新增模型对象
     * @return 模型id
     */
    @Override
    @Transactional
    public String saveModel(ModelAddDTO modelAddDTO) {
        String key = modelAddDTO.getKey();
        String name = modelAddDTO.getName();
        // 默认加载空模型模板，替换带@参数
        String emptyModelString = ResourceUtil.readUtf8Str("config/EmptyModel.bpmn")
            .replaceAll("@key", key)
            .replaceAll("@name", name);
        byte[] emptyModel = StrUtil.bytes(emptyModelString);
        // 创建modal
        Model model = repositoryService.newModel();
        model.setKey(key);
        model.setName(name);
        model.setCategory(modelAddDTO.getCategory());
        model.setVersion(Integer.parseInt(
            String.valueOf(repositoryService.createModelQuery()
                .modelKey(model.getKey())
                .count() + 1)));
        FlowSecurityUtil.loginAs();
        // 保存模型
        repositoryService.saveModel(model);
        // 保存模型的xml
        repositoryService.addModelEditorSource(model.getId(), emptyModel);
        // 更新模型扩展字段
        UpdateChain.of(ActReModel.class)
            .set(ActReModel::getWebPath, modelAddDTO.getWebPath())
            .set(ActReModel::getCodeRuleId, modelAddDTO.getCodeRuleId())
            .set(ActReModel::getSort, modelAddDTO.getSort(), modelAddDTO.getSort() != null)
            .set(ActReModel::getDepiction, modelAddDTO.getDepiction())
            .eq(ActReModel::getId, model.getId())
            .update();
        return model.getId();
    }

    /**
     * 通过id修改模型
     *
     * @param modelUpdateDTO 更新模型对象
     * @return Boolean
     */
    @Override
    public boolean updateModelById(ModelUpdateDTO modelUpdateDTO) {
        UpdateChain.of(ActReModel.class)
            .set(ActReModel::getWebPath, modelUpdateDTO.getWebPath())
            .set(ActReModel::getCodeRuleId, modelUpdateDTO.getCodeRuleId())
            .set(ActReModel::getDepiction, modelUpdateDTO.getDescription())
            .set(ActReModel::getSort, modelUpdateDTO.getSort())
            .eq(ActReModel::getId, modelUpdateDTO.getId())
            .update();
        return true;
    }

    /**
     * 通过id删除
     *
     * @param ids ids
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean removeModelByIds(List<String> ids) {
        ids.forEach(repositoryService::deleteModel);
        return true;
    }

    /**
     * 列表查询
     *
     * @param modelQuery 查询对象
     * @return 列表
     */
    @Override
    public List<ModelVO> listModel(ModelQuery modelQuery) {
        return getQueryChainByQuery(modelQuery).listAs(ModelVO.class);
    }

    /**
     * 分页查询
     *
     * @param page       分页对象
     * @param modelQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<ModelVO> pageModel(Page<ModelVO> page, ModelQuery modelQuery) {
        return getQueryChainByQuery(modelQuery).pageAs(page, ModelVO.class);
    }

    /**
     * 获取QueryChain对象
     */
    QueryChain<ActReModel> getQueryChain() {
        return queryChain().select(ACT_RE_MODEL.ALL_COLUMNS,
                ACT_EXT_CATEGORY.NAME.as(ModelVO::getCategoryName))
            .leftJoin(ACT_EXT_CATEGORY)
            .on(ACT_EXT_CATEGORY.ID.eq(ACT_RE_MODEL.CATEGORY));
    }

    /**
     * 通过userQuery获取QueryChain对象
     */
    private QueryChain<ActReModel> getQueryChainByQuery(ModelQuery modelQuery) {
        return getQueryChain()
            .and(ACT_RE_MODEL.ID.in(modelQuery.getIds()))
            .and(ACT_RE_MODEL.NAME.like(modelQuery.getName()))
            .and(ACT_RE_MODEL.KEY.like(modelQuery.getKey()))
            .and(ACT_RE_MODEL.CATEGORY.eq(modelQuery.getCategory()))
            .orderBy(ACT_RE_MODEL.SORT.asc(), ACT_RE_MODEL.CREATE_TIME.desc());
    }

    /**
     * 更新模型xml
     *
     * @param editorXmlDTO editorXmlDTO
     * @return boolean
     */
    @Override
    public boolean updateModelEditorBpmn(EditorXmlDTO editorXmlDTO) {
        String modelId = editorXmlDTO.getModelId();
        String modelXml = editorXmlDTO.getModelXml();
        // 模型xml要格式化，不然发布会丢失元素。;
        modelXml = XmlUtil.format(modelXml);
        // xml转为字节
        byte[] modelBytes = StrUtil.bytes(modelXml, CharsetUtil.UTF_8);
        updateEditorXml(modelId, modelBytes);
        return true;
    }

    void updateEditorXml(String modelId, byte[] modelBytes) {
        try {
            repositoryService.addModelEditorSource(modelId, modelBytes);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            final byte[] result = outStream.toByteArray();
            repositoryService.addModelEditorSourceExtra(modelId, result);
            outStream.close();
        } catch (Exception e) {
            logger.error("模型创建失败", e);
            throw new FlowableException("错误创建模型", e);
        }
    }

    /**
     * 通过模型id,发布流程
     *
     * @param modelId 模型id
     */
    @Override
    @Transactional
    public void deployModel(String modelId) {
        try {
            Model model = repositoryService.getModel(modelId);
            byte[] bytes = repositoryService.getModelEditorSource(modelId);
            FlowSecurityUtil.loginAs();
            deployModel(model, bytes);
        } catch (Exception e) {
            logger.error("模型发布失败", e);
            throw new CheckException("模型发布失败!");
        }
    }

    /**
     * 通过流程定义id，发布为当前版本
     *
     * @param procDefId 流程定义id
     * @return Boolean
     */
    @Override
    @Transactional
    public Boolean deployModelByProcDefId(String procDefId) {
        BpmnModel bpmnModel = actReProcdefService.getBpmnModelByDefinitionId(procDefId);
        ProcessDefinitionVO processDefinitionVO = actReProcdefService.getProcessDefinitionById(procDefId);
        String modelId = processDefinitionVO.getModelId();
        Model model = repositoryService.getModel(modelId);
        BpmnXMLConverter converter = new BpmnXMLConverter();
        byte[] modelBytes = converter.convertToXML(bpmnModel);
        FlowSecurityUtil.loginAs();
        deployModel(model, modelBytes);
        // 替换当前模型的modelXml
        updateEditorXml(modelId, modelBytes);
        return true;
    }

    /**
     * 通过id查询模型xml
     *
     * @param id 模型id
     * @return 模型xml
     */
    @Override
    public String getModelBpmn(String id) {
        byte[] source = repositoryService.getModelEditorSource(id);
        return XmlUtil.format(StrUtil.str(source, CharsetUtil.UTF_8));
    }

    /**
     * 发布流程
     *
     * @param model 模型对象
     * @param bytes bpmn
     */
    private void deployModel(Model model, byte[] bytes) {
        try {
            if (bytes == null) {
                throw new CheckException("模型数据为空，请先完成流程并保存，然后再部署!");
            }
            // 获取当前模型正在编辑、最新的xml
            byte[] source = repositoryService.getModelEditorSource(model.getId());
            String modelXml = StrUtil.str(source, CharsetUtil.UTF_8);
            BpmnModel bpmnModel = new BpmnXMLConverter()
                .convertToBpmnModel(new StringStreamSource(modelXml), true, true);
            if (bpmnModel.getProcesses()
                .isEmpty()) {
                throw new CheckException("模型不符要求，请至少设计一条主线流程!");
            }
            String processName = model.getName();
            if (!StrUtil.endWithIgnoreCase(processName, BPMN20_XML)) {
                processName += BPMN20_XML;
            }
            // 官方API验证流程定义是针对特定场景的，不全。
            // 要全面的验证目前还是再前端做验证
            List<ValidationError> validationErrors = repositoryService.validateProcess(bpmnModel);
            if (!validationErrors.isEmpty()) {
                String errorMessage = validationErrors.stream()
                    .map(error -> StrUtil.format("模型不符要求，节点名称:{},错误规则:{},说明:{}",
                        error.getActivityName(),
                        error.getProblem(),
                        error.getDefaultDescription())
                    )
                    .collect(Collectors.joining(";"));
                throw new CheckException(errorMessage);
            }
            // 部署流程
            Deployment deployment = repositoryService
                .createDeployment()
                .key(model.getKey())
                .name(model.getName())
                .addBpmnModel(processName, bpmnModel)
                .category(model.getCategory())
                .deploy();
            // 更新模型的发布信息
            ProcessDefinition procdefService = actReProcdefService.getNewestProcessDefinitionByModelKey(model.getKey());
            if (procdefService != null) {
                UpdateChain.of(ActReModel.class)
                    .set(ACT_RE_MODEL.LAST_DEPLOY_TIME, LocalDateTimeUtil.of(deployment.getDeploymentTime()))
                    .set(ACT_RE_MODEL.LAST_DEPLOY_VERSION, procdefService.getVersion())
                    .where(ACT_RE_MODEL.ID.eq(model.getId()))
                    .update();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CheckException("部署失败!");
        }
    }
}