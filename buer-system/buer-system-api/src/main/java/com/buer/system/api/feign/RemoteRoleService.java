package com.buer.system.api.feign;

import com.buer.common.core.constant.ServiceNameConstants;
import com.buer.common.core.entity.R;
import com.buer.common.feign.component.FeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 用户 Feign
 *
 * @author zoulan
 * @since 2023-05-07
 */
@FeignClient(contextId = "RemoteRoleService", value = ServiceNameConstants.SYSTEM_SERVER, configuration = FeignInterceptor.class)
public interface RemoteRoleService {

    /**
     * 通过用户id，查询角色ids
     *
     * @param userId userId
     * @return R
     */
    @GetMapping(value = "/role/user/{userId}/getRoleIds")
    R<List<String>> getRoleIdsByUserId(@PathVariable(value = "userId") Long userId);

    /**
     * 通过用户id，查询角色ids
     *
     * @param roleId roleId
     * @return R
     */
    @GetMapping(value = "/role/permission/list/{roleId}")
    R<List<String>> getPermissionsByRoleId(@PathVariable("roleId") Long roleId);

}
