package com.buer.flow.biz.service;

import com.buer.flow.api.dto.EditorXmlDTO;
import com.buer.flow.api.dto.ModelAddDTO;
import com.buer.flow.api.dto.ModelUpdateDTO;
import com.buer.flow.api.entity.ActReModel;
import com.buer.flow.api.query.ModelQuery;
import com.buer.flow.api.vo.ModelVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 流程定义 Service
 *
 * @author zoulan
 * @since 2023-05-11
 */
public interface ActReModelService extends IService<ActReModel> {

    /**
     * 通过id查询模型
     *
     * @param id id
     * @return 模型信息
     */
    ModelVO getModelById(String id);

    /**
     * 通过模型key 查询模型
     *
     * @param key 模型key
     * @return 模型信息
     */
    ModelVO getModelByKey(String key);

    /**
     * 新增
     *
     * @param modelAddDTO 模型对象
     * @return Boolean
     */
    String saveModel(ModelAddDTO modelAddDTO);

    /**
     * 通过id修改
     *
     * @param modelUpdateDTO 模型对象
     * @return Boolean
     */
    boolean updateModelById(ModelUpdateDTO modelUpdateDTO);

    /**
     * 通过id删除 型
     *
     * @param modelIds modelIds
     * @return Boolean
     */
    boolean removeModelByIds(List<String> modelIds);

    /**
     * 列表查询模型
     *
     * @param entity 查询对象
     * @return 列表
     */
    List<ModelVO> listModel(ModelQuery entity);

    /**
     * 分页查询模型
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    Page<ModelVO> pageModel(Page<ModelVO> page, ModelQuery entity);

    /**
     * 更新模型xml
     *
     * @param editorXmlDTO editorXmlDTO
     * @return boolean
     */
    boolean updateModelEditorBpmn(EditorXmlDTO editorXmlDTO);

    /**
     * 通过模型id,发布流程
     *
     * @param modelId 模型id
     */
    void deployModel(String modelId);

    /**
     * 通过流程定义id，发布为当前版本
     *
     * @param procDefId 流程定义id
     * @return Boolean
     */
    Boolean deployModelByProcDefId(String procDefId);

    /**
     * 通过id查询模型xml
     *
     * @param id 模型id
     * @return 模型xml
     */
    String getModelBpmn(String id);
}