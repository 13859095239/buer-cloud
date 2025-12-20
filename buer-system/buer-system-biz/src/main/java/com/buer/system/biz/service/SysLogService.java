package com.buer.system.biz.service;

import com.buer.system.api.entity.SysLog;
import com.buer.system.api.query.LogQuery;
import com.buer.system.api.vo.LogVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 日志 Service
 *
 * @author zoulan
 * @since 2023-05-11
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 通过id查询日志
     *
     * @param id id
     * @return 日志信息
     */
    LogVO getLogById(Long id);

    /**
     * 新增日志
     *
     * @param log 日志对象
     * @return Boolean
     */
    boolean addLog(SysLog log);

    /**
     * 通过id修改日志
     *
     * @param log 日志对象
     * @return Boolean
     */
    boolean updateLog(SysLog log);

    /**
     * 通过ids删除日志
     *
     * @param logIds logIds
     * @return Boolean
     */
    boolean removeLogByIds(List<Long> logIds);

    /**
     * 列表查询日志
     *
     * @param logQuery 查询对象
     * @return 列表
     */
    List<LogVO> listLog(LogQuery logQuery);

    /**
     * 分页查询日志
     *
     * @param page     分页对象
     * @param logQuery 查询对象
     * @return 分页对象
     */
    Page<LogVO> pageLog(Page<LogVO> page, LogQuery logQuery);

}