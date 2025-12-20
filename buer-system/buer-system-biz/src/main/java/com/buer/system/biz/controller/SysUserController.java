package com.buer.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.common.security.util.SecurityUtils;
import com.buer.system.api.dto.*;
import com.buer.system.api.entity.SysUser;
import com.buer.system.api.query.UserQuery;
import com.buer.system.api.vo.UserForLoginVO;
import com.buer.system.api.vo.UserInfoVO;
import com.buer.system.api.vo.UserLabelVO;
import com.buer.system.api.vo.UserVO;
import com.buer.system.biz.service.SysUserService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户 Controller
 *
 * @author zoulan
 * @since 2023-05-07
 */
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "用户")
public class SysUserController {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder(12);
    private final SysUserService service;

    /**
     * 通过id查询用户
     *
     * @param id id
     * @return UserVO
     */
    @Operation(summary = "通过id查询用户")
    @GetMapping(value = "/{id}")
    public R<UserVO> getUserById(@PathVariable Long id) {
        return R.ok(service.getUserById(id));
    }

    /**
     * 新增用户
     *
     * @param addUserDTO 新增用户 DTO
     * @return Boolean
     */
    @Operation(summary = "新增用户")
    @PostMapping
    @SaCheckPermission("user-add")
    public R<Boolean> addUser(@RequestBody @Valid AddUserDTO addUserDTO) {
        return R.ok(service.addUser(addUserDTO));
    }

    /**
     * 通过id修改用户
     *
     * @param updateUserDTO 更新用户 DTO
     * @return Boolean
     */
    @Operation(summary = "通过id修改用户")
    @PutMapping
    @SaCheckPermission("user-edit")
    public R<Boolean> updateUser(@RequestBody @Valid UpdateUserDTO updateUserDTO) {
        return R.ok(service.updateUser(updateUserDTO));
    }

    /**
     * 通过id删除用户
     *
     * @param userIds userIds
     * @return Boolean
     */
    @SysLog("删除用户")
    @Operation(summary = "通过id删除用户")
    @DeleteMapping
    @SaCheckPermission("user-delete")
    public R<Boolean> removeUserByIds(@RequestBody List<Long> userIds) {
        return R.ok(service.removeUserByIds(userIds));
    }

    /**
     * 列表查询用户
     *
     * @param entity 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询用户")
    @GetMapping(value = "/list")
    public R<List<UserVO>> listUser(UserQuery entity) {
        return R.ok(service.listUser(entity));
    }

    /**
     * 分页查询用户
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询用户")
    @GetMapping(value = "/page")
    public R<Page<UserVO>> pageUser(Page<UserVO> page, UserQuery entity) {
        return R.ok(service.pageUser(page, entity));
    }

    /**
     * 通过用户名查询用户信息
     * 用于登录
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Operation(summary = "通过用户名查询用户信息")
    @GetMapping("/info/{username}")
    public R<UserForLoginVO> info(@PathVariable("username") String username) {
        return R.ok(service.getByUserName(username));
    }

    /**
     * 查询当前登录的用户信息
     * 用于登录后查询
     *
     * @return 用户信息
     */
    @Operation(summary = "查询当前登录的用户信息")
    @GetMapping("/my/userInfo")
    public R<UserInfoVO> getMyUserInfo() {
        return R.ok(service.getUserInfo());
    }

    /**
     * 查询当前用户基本信息
     *
     * @return 用户信息
     */
    @Operation(summary = "查询当前用户基本信息")
    @GetMapping("/my/user")
    public R<UserVO> getMyUser() {
        UserVO userVO = service.getUserById(SecurityUtils.getUserId());
        return R.ok(userVO);
    }

    /**
     * 修改当前用户基本信息
     *
     * @param entity 当前用户基本信息DTO
     * @return 用户信息
     */
    @SysLog("修改当前用户基本信息")
    @Operation(summary = "修改当前用户基本信息")
    @PutMapping("/my")
    public R<Boolean> updateMyUser(@RequestBody MyUserDTO entity) {
        SysUser model = new SysUser().setId(SecurityUtils.getUserId());
        BeanUtil.copyProperties(entity, model);
        return R.ok(service.updateById(model));
    }

    /**
     * 修改自身用户密码
     *
     * @param userResetMyPasswordDTO 用户重置密码DTO
     * @return 用户信息
     */
    @SysLog("修改自身用户密码")
    @Operation(summary = "修改自身用户密码")
    @PutMapping("/my/resetPassword")
    public R<Boolean> resetMyPassword(@RequestBody @Valid UserResetMyPasswordDTO userResetMyPasswordDTO) {
        Long userId = SecurityUtils.getUserId();
        String newPassword = userResetMyPasswordDTO.getNewPassword();
        String oldPassword = userResetMyPasswordDTO.getOldPassword();
        String confirmPassword = userResetMyPasswordDTO.getConfirmPassword();
        SysUser user = service.getOne(QueryWrapper.create()
            .eq(SysUser::getId, SecurityUtils.getUserId()));
        // 判断新密码不能为空
        Assert.isTrue(StrUtil.isNotBlank(newPassword), "新密码不能为空");
        // 判断旧密码是否正确
        Assert.isTrue(ENCODER.matches(oldPassword, user.getPassword()), "旧密码不正确");
        // 判断新密码与确认密码一致
        Assert.isTrue(StrUtil.equals(newPassword, confirmPassword), "新密码与确认密码不一致");
        UserResetPasswordDTO userResetPasswordDTO = new UserResetPasswordDTO().setId(userId)
            .setNewPassword(newPassword);
        return R.ok(service.resetPassword(userResetPasswordDTO));
    }

    /**
     * 通过用户id修改用户密码
     * 适用于管理员修改某个用户密码
     *
     * @param userResetPasswordDTO 用户重置密码DTO
     * @return Boolean
     */
    @SysLog("修改用户密码")
    @Operation(summary = "通过用户id修改某个密码")
    @PutMapping("/resetPassword")
    @SaCheckPermission("user-edit")
    public R<Boolean> resetPassword(@RequestBody @Valid UserResetPasswordDTO userResetPasswordDTO) {
        return R.ok(service.resetPassword(userResetPasswordDTO));
    }

    /**
     * 列表查询，用户外键数据
     *
     * @param userIds 用户id列表
     * @return 列表
     */
    @Operation(summary = "列表查询，用户外键数据")
    @PostMapping(value = "/listUserLabelByIds")
    public R<List<UserLabelVO>> listUserLabelByIds(@RequestBody List<Long> userIds) {
        List<UserLabelVO> users = service.listUserLabelByIds(userIds);
        return R.ok(users);
    }

    /**
     * 导出用户数据
     *
     * @param response  HTTP响应对象
     * @param userQuery 查询条件
     */
    @SysLog("导出用户数据")
    @Operation(summary = "导出用户数据")
    @PostMapping("/export")
    @SaCheckPermission("user-export")
    public void exportUser(HttpServletResponse response, @RequestBody UserQuery userQuery) {
        service.exportUser(response, userQuery);
    }

    /**
     * 文件上传接口
     * 用于Excel导入组件的文件上传
     *
     * @param file 上传的Excel文件
     * @return 文件ID
     */
    @Operation(summary = "文件上传")
    @PostMapping("/upload")
    @SaCheckPermission("user-import")
    public R<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        // 这里应该调用文件服务，返回文件ID
        // 为了演示，我们返回一个模拟的文件ID
        Map<String, String> result = new HashMap<>();
        result.put("fileId", "file_" + System.currentTimeMillis());
        result.put("fileName", file.getOriginalFilename());
        return R.ok(result);
    }

    /**
     * 导入用户数据
     *
     * @param file 上传的Excel文件
     * @return 导入结果信息
     */
    @SysLog("导入用户数据")
    @Operation(summary = "导入用户数据")
    @PostMapping("/import")
    @SaCheckPermission("user-import")
    public R<String> importUser(@RequestParam("file") MultipartFile file) {
        String result = service.importUser(file);
        return R.ok(result);
    }

    /**
     * 异步导入用户数据
     * 支持EventSource进度监听
     *
     * @param request 导入请求
     * @return 任务ID
     */
    @SysLog("异步导入用户数据")
    @Operation(summary = "异步导入用户数据")
    @PostMapping("/import/async")
    @SaCheckPermission("user-import")
    public R<Map<String, String>> importUserAsync(@RequestBody Map<String, Object> request) {
        String fileId = (String) request.get("fileId");
        Boolean enableProgress = (Boolean) request.getOrDefault("enableProgress", false);
        
        // 生成任务ID
        String taskId = "task_" + System.currentTimeMillis();
        
        // 这里应该启动异步任务
        // 为了演示，我们返回任务ID
        Map<String, String> result = new HashMap<>();
        result.put("taskId", taskId);
        result.put("message", "导入任务已创建");
        
        return R.ok(result);
    }

    /**
     * 查询导入任务状态
     * 用于EventSource进度监听
     *
     * @param taskId 任务ID
     * @return 任务状态
     */
    @Operation(summary = "查询导入任务状态")
    @GetMapping("/import/status/{taskId}")
    public R<Map<String, Object>> getImportStatus(@PathVariable String taskId) {
        // 这里应该查询实际的任务状态
        // 为了演示，我们返回模拟状态
        Map<String, Object> status = new HashMap<>();
        status.put("taskId", taskId);
        status.put("status", "completed");
        status.put("progress", 100);
        status.put("message", "导入完成");
        
        // 模拟导入结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", 100);
        result.put("success", 95);
        result.put("failed", 5);
        result.put("errors", List.of(
            Map.of("row", 10, "column", "手机号", "message", "手机号格式不正确"),
            Map.of("row", 25, "column", "邮箱", "message", "邮箱格式不正确")
        ));
        status.put("result", result);
        
        return R.ok(status);
    }

    /**
     * 取消导入任务
     *
     * @param taskId 任务ID
     * @return 取消结果
     */
    @Operation(summary = "取消导入任务")
    @PostMapping("/import/cancel/{taskId}")
    public R<Boolean> cancelImport(@PathVariable String taskId) {
        // 这里应该取消实际的任务
        // 为了演示，我们返回成功
        return R.ok(true);
    }

    /**
     * 下载用户导入模板
     *
     * @param response HTTP响应对象
     */
    @Operation(summary = "下载用户导入模板")
    @GetMapping("/template")
    public void downloadUserTemplate(HttpServletResponse response) {
        try {
            service.downloadUserTemplate(response);
        } catch (Exception e) {
            log.error("下载用户导入模板失败", e);
            try {
                response.reset();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":500,\"message\":\"模板下载失败：" + e.getMessage() + "\"}");
            } catch (Exception ex) {
                log.error("写入错误响应失败", ex);
            }
        }
    }

}