package com.buer.system.api.feign;

import com.buer.common.core.constant.ServiceNameConstants;
import com.buer.common.core.entity.R;
import com.buer.common.feign.component.FeignInterceptor;
import com.buer.system.api.vo.DictItemLabelVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 字典 Feign
 *
 * @author zoulan
 * @since 2025-09-10
 */
@FeignClient(
    contextId = "remoteDictService",
    name = ServiceNameConstants.SYSTEM_SERVER,
    configuration = FeignInterceptor.class
)
public interface RemoteDictService {
    /**
     * 列表查询，字典外键数据
     *
     * @param dictKeys 字典dictKeys列表
     * @return 列表
     */
    @PostMapping("/dict/listDictLabelByDictKeys")
    R<List<DictItemLabelVO>> listDictLabelByDictKeys(@RequestBody List<String> dictKeys);
}
