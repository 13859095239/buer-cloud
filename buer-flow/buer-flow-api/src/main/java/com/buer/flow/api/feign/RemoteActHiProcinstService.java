package com.buer.flow.api.feign;

import com.buer.common.core.constant.ServiceNameConstants;
import com.buer.common.core.entity.R;
import com.buer.common.feign.component.FeignInterceptor;
import com.buer.flow.api.dto.CreateProcinstDTO;
import com.buer.flow.api.vo.CreatedProcinstVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 流程实例 Feign
 *
 * @author zoulan
 * @since 2024-06-07
 */
@FeignClient(path = "procinst", contextId = "RemoteActHiProcinstService", value = ServiceNameConstants.FLOW_SERVICE, configuration = FeignInterceptor.class)
public interface RemoteActHiProcinstService {

    /**
     * 创建流程
     *
     * @param createProcinstDTO 创建流程对象
     * @return CreatedProcinstVO
     */
    @Operation(summary = "创建流程")
    @PostMapping("/createProcinst")
    R<CreatedProcinstVO> createProcinst(@RequestBody CreateProcinstDTO createProcinstDTO);

    /**
     * 通过id删除流程实例
     *
     * @param procinstIds procinstIds
     * @return Boolean
     */
    @Operation(summary = "通过id删除流程实例")
    @DeleteMapping
    R<Boolean> removeProcinstByIds(@RequestBody List<String> procinstIds);
}
