package com.buer.system.api.feign;

import com.buer.common.core.constant.ServiceNameConstants;
import com.buer.common.core.entity.R;
import com.buer.common.feign.component.FeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 编号规则 Feign
 *
 * @author zoulan
 * @since 2024-06-07
 */
@FeignClient(contextId = "remoteCodeRuleService", value = ServiceNameConstants.SYSTEM_SERVER, configuration = FeignInterceptor.class)
public interface RemoteCodeRuleService {

    /**
     * 通过编号规则id获取编号
     *
     * @param id 编号规则id
     * @return 编号
     */
    @GetMapping("/code-rule/generateCode/id/{id}")
    R<String> generateCodeById(@PathVariable(value = "id") Long id);

    /**
     * 通过编号规则key获取编号
     *
     * @param key 编号规则key
     * @return 编号
     */
    @GetMapping("/code-rule/generateCode/key/{key}")
    R<String> generateCodeByKey(@PathVariable(value = "key") String key);

}
