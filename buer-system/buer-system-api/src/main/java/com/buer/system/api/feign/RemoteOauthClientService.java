package com.buer.system.api.feign;

import com.buer.common.core.constant.ServiceNameConstants;
import com.buer.common.core.entity.R;
import com.buer.common.feign.component.FeignInterceptor;
import com.buer.system.api.entity.SysOauthClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * oauth终端 Feign
 *
 * @author zoulan
 * @since 2024-09-07
 */
@FeignClient(
    contextId = "remoteOauthClientDetailsService",
    name = ServiceNameConstants.SYSTEM_SERVER,
    configuration = FeignInterceptor.class
)
public interface RemoteOauthClientService {

    /**
     * 通过id查询oauth终端
     *
     * @param clientId 终端clientId
     * @return R
     */
    @GetMapping("/oauth-client/{clientId}")
    R<SysOauthClient> getOauthClientDetailsByClientId(@PathVariable("clientId") String clientId);
}
