package com.buer.system.api.feign;

import com.buer.common.core.constant.ServiceNameConstants;
import com.buer.common.core.entity.R;
import com.buer.common.feign.component.FeignInterceptor;
import com.buer.system.api.vo.UserForLoginVO;
import com.buer.system.api.vo.UserLabelVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 用户 Feign
 *
 * @author zoulan
 * @since 2023-05-07
 */
@FeignClient(
    contextId = "remoteUserService",
    name = ServiceNameConstants.SYSTEM_SERVER,
    configuration = FeignInterceptor.class
)
public interface RemoteUserService {

    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @return R
     */
    @GetMapping("/user/info/{username}")
    R<UserForLoginVO> info(@PathVariable("username") String username);

    /**
     * 通过手机号码查询用户、角色信息
     *
     * @param phone 手机号码
     * @return R
     */
    @GetMapping("/app/info/{phone}")
    R<UserForLoginVO> infoByMobile(@PathVariable("phone") String phone);

    /**
     * 根据部门id，查询对应的用户 id 集合
     *
     * @param deptIds 部门id 集合
     * @return 用户 id 集合
     */
    @GetMapping("/user/ids")
    R<List<Long>> listUserIdByDeptIds(@RequestParam("deptIds") Set<Long> deptIds);

    /**
     * 列表查询，用户外键数据
     *
     * @param userIds 用户id列表
     * @return 列表
     */
    @PostMapping("/user/listUserLabelByIds")
    R<List<UserLabelVO>> listUserLabelByIds(@RequestBody List<Long> userIds);
}
