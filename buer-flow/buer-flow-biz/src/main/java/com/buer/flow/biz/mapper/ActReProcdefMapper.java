package com.buer.flow.biz.mapper;

import com.buer.flow.api.entity.ActReProcdef;
import com.buer.flow.api.query.ProcessDefinitionQuery;
import com.buer.flow.api.vo.ProcessDefinitionVO;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 流程定义 Mapper
 *
 * @author zoulan
 * @since 2024-06-09
 */
public interface ActReProcdefMapper extends BaseMapper<ActReProcdef> {

    /**
     * 单条记录查询流程定义
     *
     * @param id id
     * @return boolean
     */
    ProcessDefinitionVO getProcessDefinitionById(String id);

    /**
     * 列表查询流程定义
     *
     * @param query 查询对象
     * @return 列表
     */
    List<ProcessDefinitionVO> listProcessDefinitions(@Param("query") ProcessDefinitionQuery query);

    /**
     * 分页查询流程定义
     *
     * @param page  分页对象
     * @param query 查询对象
     * @return 分页对象
     */
    Page<ProcessDefinitionVO> getProcessDefinitionPage(Page<?> page, @Param("query") ProcessDefinitionQuery query);

}