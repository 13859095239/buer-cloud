package com.buer.flow.api.feign;

import com.buer.common.core.constant.ServiceNameConstants;
import com.buer.common.core.entity.R;
import com.buer.common.feign.component.FeignInterceptor;
import com.buer.flow.api.dto.CompleteTaskDTO;
import com.buer.flow.api.vo.CompletedTaskVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 待办任务 Feign
 *
 * @author zoulan
 * @since 2024-06-07
 */
@FeignClient(path = "task", contextId = "remoteActRuTaskService", value = ServiceNameConstants.FLOW_SERVICE, configuration = FeignInterceptor.class)
public interface RemoteActRuTaskService {

    /**
     * 完成任务
     *
     * @param completeTaskDTO 完成任务DTO
     * @return Boolean
     */
    @PostMapping("/completeTask")
    R<CompletedTaskVO> completeTask(@RequestBody CompleteTaskDTO completeTaskDTO);

}
