package com.buer.system.biz.service.impl;

import com.buer.common.core.util.StringUtils;
import com.buer.system.api.entity.SysTeam;
import com.buer.system.api.query.TeamQuery;
import com.buer.system.api.vo.TeamVO;
import com.buer.system.biz.mapper.SysTeamMapper;
import com.buer.system.biz.service.SysTeamService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.buer.system.api.entity.table.SysTeamTableDef.SYS_TEAM;


/**
 * 团队 ServiceImpl
 *
 * @author zoulan
 * @since 2024-05-30
 */
@Service
@RequiredArgsConstructor
public class SysTeamServiceImpl extends ServiceImpl<SysTeamMapper, SysTeam> implements SysTeamService {

    /**
     * 通过id查询团队
     *
     * @param id id
     * @return TeamVO
     */
    @Override
    public TeamVO getTeamById(Long id) {
        return getQueryChain().and(SYS_TEAM.ID.eq(id))
            .oneAs(TeamVO.class);
    }

    /**
     * 新增团队
     *
     * @param team 团队信息
     * @return boolean
     */
    @Override
    public boolean addTeam(SysTeam team) {
        return save(team);
    }

    /**
     * 通过id修改团队
     *
     * @param team 团队信息
     * @return boolean
     */
    @Override
    public boolean updateTeam(SysTeam team) {
        return updateById(team);
    }

    /**
     * 通过id删除团队
     *
     * @param teamIds teamIds
     * @return boolean
     */
    @Override
    @Transactional
    public boolean removeTeamByIds(List<Long> teamIds) {
        // List<String> idList = U.getIdListByStringWithEmptyThrow(teamIds, "团队Id");
        return removeByIds(teamIds);
    }

    /**
     * 列表查询团队
     *
     * @param teamQuery 查询对象
     * @return 列表
     */
    @Override
    public List<TeamVO> listTeam(TeamQuery teamQuery) {
        return getQueryChainByQuery(teamQuery).listAs(TeamVO.class);
    }

    /**
     * 分页查询团队
     *
     * @param page      分页对象
     * @param teamQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<TeamVO> pageTeam(Page<TeamVO> page, TeamQuery teamQuery) {
        return getQueryChainByQuery(teamQuery).pageAs(page, TeamVO.class);
    }

    /**
     * 获取QueryChain对象
     */
    private QueryChain<SysTeam> getQueryChain() {
        return queryChain().select(SYS_TEAM.ALL_COLUMNS);
    }

    /**
     * 通过query获取QueryChain对象
     */
    private QueryChain<SysTeam> getQueryChainByQuery(TeamQuery teamQuery) {
        return getQueryChain()
            .and(SYS_TEAM.ID.in(StringUtils.arrayBySplit(teamQuery.getTeamIds())))
            .and(SYS_TEAM.NAME.like(teamQuery.getName()))
            .orderBy(SYS_TEAM.CREATE_TIME.desc());
    }

}