package com.buer.system.api.feign;

import com.buer.common.core.constant.ServiceNameConstants;
import com.buer.common.core.entity.R;
import com.buer.common.feign.component.FeignInterceptor;
import com.buer.system.api.vo.PostLabelVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 岗位 Feign
 *
 * @author zoulan
 * @since 2025-09-11
 */
@FeignClient(
    contextId = "remotePostService",
    name = ServiceNameConstants.SYSTEM_SERVER,
    configuration = FeignInterceptor.class
)
public interface RemotePostService {

    /**
     * 列表查询，岗位外键数据
     *
     * @param postIds 岗位id列表
     * @return 列表
     */
    @PostMapping("/post/listPostLabelByIds")
    R<List<PostLabelVO>> listPostLabelByIds(@RequestBody List<Long> postIds);
}


