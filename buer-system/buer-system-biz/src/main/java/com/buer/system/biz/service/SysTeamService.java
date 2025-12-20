package com.buer.system.biz.service;

import com.buer.system.api.entity.SysTeam;
import com.buer.system.api.query.TeamQuery;
import com.buer.system.api.vo.TeamVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 团队 Service
 *
 * @author zoulan
 * @since 2024-05-30
 */
public interface SysTeamService extends IService<SysTeam> {

    /**
     * 通过id查询团队
     *
     * @param id id
     * @return 团队信息
     */
    TeamVO getTeamById(Long id);

    /**
     * 新增团队
     *
     * @param team 团队信息
     * @return boolean
     */
    boolean addTeam(SysTeam team);

    /**
     * 通过id修改团队
     *
     * @param team 团队信息
     * @return boolean
     */
    boolean updateTeam(SysTeam team);

    /**
     * 通过id删除团队
     *
     * @param teamIds teamIds
     * @return boolean
     */
    boolean removeTeamByIds(List<Long> teamIds);

    /**
     * 列表查询团队
     *
     * @param teamQuery 查询对象
     * @return 列表
     */
    List<TeamVO> listTeam(TeamQuery teamQuery);

    /**
     * 分页查询团队
     *
     * @param page      分页对象
     * @param teamQuery 查询对象
     * @return 分页对象
     */
    Page<TeamVO> pageTeam(Page<TeamVO> page, TeamQuery teamQuery);

}