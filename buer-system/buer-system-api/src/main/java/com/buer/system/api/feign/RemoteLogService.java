package com.buer.system.api.feign;

import com.buer.common.core.constant.ServiceNameConstants;
import com.buer.common.core.entity.R;
import com.buer.common.feign.component.FeignInterceptor;
import com.buer.system.api.entity.SysLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 日志 Feign
 *
 * @author zoulan
 * @since 2023-06-08
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.SYSTEM_SERVER, configuration = FeignInterceptor.class)
public interface RemoteLogService {

    /**
     * 保存日志
     *
     * @param sysLog 日志实体
     * @return succes、false
     */
    @PostMapping(value = "/log")
    R<Boolean> saveLog(@RequestBody SysLog sysLog);

}
