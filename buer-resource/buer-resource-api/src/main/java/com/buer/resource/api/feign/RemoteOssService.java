package com.buer.resource.api.feign;

import com.buer.common.core.constant.ServiceNameConstants;
import com.buer.common.core.entity.R;
import com.buer.common.feign.component.FeignInterceptor;
import com.buer.resource.api.dto.OssFileGroupDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 文件 Feign
 *
 * @author zoulan
 * @since 2023-06-08
 */
@FeignClient(name = ServiceNameConstants.RESOURCE_SERVICE, configuration = FeignInterceptor.class)
public interface RemoteOssService {

    /**
     * 保存文件
     *
     * @param ossUpdateDTO 文件上传DTO
     * @return R
     */
    @PutMapping(value = "/oss/file-group")
    R<Boolean> updateFileGroup(@RequestBody OssFileGroupDTO ossUpdateDTO);

    /**
     * 保存文件
     *
     * @param ossUpdateDTOList 文件上传DTO
     * @return R
     */
    @PutMapping(value = "/oss/file-group-list")
    R<Boolean> updateFileGroup(@RequestBody List<OssFileGroupDTO> ossUpdateDTOList);

    /**
     * 通过fileGroupId 删除文件
     *
     * @param fileGroupId 文件组id,多个逗号隔开
     * @return R<Boolean>
     */
    @DeleteMapping(value = "/oss/file-group-id/{fileGroupId}")
    R<Boolean> deleteOssByFileGroupId(@PathVariable(value = "fileGroupId") String fileGroupId);
}
