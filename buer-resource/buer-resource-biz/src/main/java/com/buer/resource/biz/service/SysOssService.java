package com.buer.resource.biz.service;

import com.buer.resource.api.dto.OssFileGroupDTO;
import com.buer.resource.api.dto.OssUploadDTO;
import com.buer.resource.api.entity.SysOss;
import com.buer.resource.api.query.OssQuery;
import com.buer.resource.api.vo.OssVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * OSS对象存储表 Service
 *
 * @author zoulan
 * @since 2023-08-27
 */
public interface SysOssService extends IService<SysOss> {

    /**
     * 通过id查询文件
     *
     * @param id 文件id
     * @return 文件信息
     */
    OssVO getOssById(Long id);

    /**
     * 上传文件
     *
     * @param ossUploadDTO 文件上传DTO
     * @return OssVO
     */
    OssVO upload(OssUploadDTO ossUploadDTO);


    /**
     * 下载文件
     *
     * @param ossId    文件id
     * @param response 输出内容
     */
    void download(Long ossId, HttpServletResponse response) throws IOException;

    /**
     * 更新文件组
     *
     * @param entity OssFileGroupDTO
     * @return Boolean
     */
    boolean updateFileGroup(OssFileGroupDTO entity);

    /**
     * 更新文件组列表
     *
     * @param ossFileGroupDTOList List<OssFileGroupDTO>
     * @return Boolean
     */
    boolean updateFileGroupList(List<OssFileGroupDTO> ossFileGroupDTOList);

    /**
     * 通过id删除文件
     *
     * @param ossIds 文件id列表
     * @return Boolean
     */
    boolean removeOssByIds(List<Long> ossIds);

    /**
     * 通过fileGroupId 删除文件
     *
     * @param fileGroupId 文件组id,多个逗号隔开
     * @return Boolean
     */
    boolean removeOssByFileGroupId(String fileGroupId);

    /**
     * 列表查询文件
     *
     * @param entity 查询对象
     * @return 列表
     */
    List<OssVO> listOss(OssQuery entity);

    /**
     * 分页查询文件
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    Page<OssVO> pageOss(Page<OssVO> page, OssQuery entity);

}