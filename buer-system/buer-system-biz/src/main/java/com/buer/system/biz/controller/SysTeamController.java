package com.buer.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.system.api.dto.AddTeamUserDTO;
import com.buer.system.api.entity.SysTeam;
import com.buer.system.api.query.TeamQuery;
import com.buer.system.api.query.TeamUserQuery;
import com.buer.system.api.vo.TeamVO;
import com.buer.system.api.vo.UserVO;
import com.buer.system.biz.service.SysTeamService;
import com.buer.system.biz.service.SysTeamUserService;
import com.buer.system.biz.service.SysUserService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 团队 Controller
 *
 * @author zoulan
 * @since 2024-05-30
 */
@RestController
@RequestMapping("team")
@RequiredArgsConstructor
@Tag(name = "团队")
public class SysTeamController {

    private final SysTeamService service;
    private final SysTeamUserService sysTeamUserService;
    private final SysUserService sysUserService;

    /**
     * 通过id查询团队
     *
     * @param id id
     * @return TeamVO
     */
    @Operation(summary = "通过id查询团队")
    @GetMapping(value = "/{id}")
    public R<TeamVO> getTeamById(@PathVariable Long id) {
        return R.ok(service.getTeamById(id));
    }

    /**
     * 新增团队
     *
     * @param team 团队信息
     * @return boolean
     */
    @SysLog(value = "新增团队")
    @Operation(summary = "新增团队")
    @PostMapping
    @SaCheckPermission("team-add")
    public R<Boolean> addTeam(@RequestBody SysTeam team) {
        return R.ok(service.addTeam(team));
    }

    /**
     * 通过id修改团队
     *
     * @param team 团队信息
     * @return boolean
     */
    @SysLog(value = "修改团队")
    @Operation(summary = "通过id修改团队")
    @PutMapping
    @SaCheckPermission("team-edit")
    public R<Boolean> updateTeam(@RequestBody SysTeam team) {
        return R.ok(service.updateTeam(team));
    }

    /**
     * 通过id删除团队
     *
     * @param teamIds teamIds
     * @return boolean
     */
    @SysLog(value = "删除团队")
    @Operation(summary = "通过id删除团队")
    @DeleteMapping
    @SaCheckPermission("team-delete")
    public R<Boolean> removeTeamByIds(@RequestBody List<Long> teamIds) {
        return R.ok(service.removeTeamByIds(teamIds));
    }

    /**
     * 列表查询团队
     *
     * @param teamQuery 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询团队")
    @GetMapping(value = "/list")
    public R<List<TeamVO>> listTeam(TeamQuery teamQuery) {
        return R.ok(service.listTeam(teamQuery));
    }

    /**
     * 分页查询团队
     *
     * @param page      分页对象
     * @param teamQuery 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询团队")
    @GetMapping(value = "/page")
    public R<Page<TeamVO>> pageTeam(Page<TeamVO> page, TeamQuery teamQuery) {
        return R.ok(service.pageTeam(page, teamQuery));
    }

    /**
     * 新增团队下的用户
     *
     * @param addTeamUserDTO AddTeamUserDTO
     * @return R<Boolean>
     */
    @SysLog("新增团队下的用户")
    @Operation(summary = "新增团队下的用户")
    @PostMapping(value = "/user")
    @SaCheckPermission("team-add")
    public R<Boolean> addTeamUser(@RequestBody AddTeamUserDTO addTeamUserDTO) {
        return R.ok(sysTeamUserService.addTeamUser(addTeamUserDTO));
    }

    /**
     * 获取团队下的用户列表
     * 获取团队下未添加的用户列表
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 用户的分页对象
     */
    @GetMapping(value = "/user/page")
    public R<Page<UserVO>> getUserPageByTeam(Page<UserVO> page, TeamUserQuery entity) {
        return R.ok(sysUserService.pageUserByTeam(page, entity));
    }

    /**
     * 删除团队下的用户
     *
     * @param teamId  团队id
     * @param userIds 要删除的用户id列表
     * @return Boolean
     */
    @SysLog("删除团队下的用户")
    @Operation(summary = "删除团队下的用户")
    @DeleteMapping(value = "/user")
    @SaCheckPermission("team-delete")
    public R<Boolean> deleteTeamUser(Long teamId, Long[] userIds) {
        return R.ok(sysTeamUserService.deleteTeamUser(teamId, userIds));
    }
}