package com.buer.system.biz.service.impl;

import com.buer.system.api.entity.SysLog;
import com.buer.system.api.query.LogQuery;
import com.buer.system.api.vo.LogVO;
import com.buer.system.biz.mapper.SysLogMapper;
import com.buer.system.biz.service.SysLogService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.buer.system.api.entity.table.SysLogTableDef.SYS_LOG;


/**
 * 日志 ServiceImpl
 *
 * @author zoulan
 * @since 2023-05-11
 */
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    /**
     * 通过id查询日志
     *
     * @param id id
     * @return LogVO
     */
    @Override
    public LogVO getLogById(Long id) {
        return queryChain().and(SYS_LOG.ID.eq(id))
            .oneAs(LogVO.class);
    }

    /**
     * 新增日志
     *
     * @param log 日志对象
     * @return Boolean
     */
    @Override
    public boolean addLog(SysLog log) {
        return save(log);
    }

    /**
     * 通过id修改日志
     *
     * @param log 日志对象
     * @return Boolean
     */
    @Override
    public boolean updateLog(SysLog log) {
        return updateById(log);
    }

    /**
     * 通过id删除日志
     *
     * @param logIds logIds
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean removeLogByIds(List<Long> logIds) {
        // List<String> logIdList = U.getIdListByStringWithEmptyThrow(logIds, "日志Id");
        return removeByIds(logIds);
    }

    /**
     * 列表查询日志
     *
     * @param logQuery 查询对象
     * @return 列表
     */
    @Override
    public List<LogVO> listLog(LogQuery logQuery) {
        return queryChain().listAs(LogVO.class);
    }

    /**
     * 分页查询日志
     *
     * @param page     分页对象
     * @param logQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<LogVO> pageLog(Page<LogVO> page, LogQuery logQuery) {
        return queryChain().pageAs(page, LogVO.class);
    }

}