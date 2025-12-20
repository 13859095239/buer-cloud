package com.buer.resource.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.buer.common.core.exception.CheckException;
import com.buer.common.core.util.FileUtils;
import com.buer.common.oss.core.OssClient;
import com.buer.common.oss.entity.UploadResult;
import com.buer.common.oss.enumd.AccessPolicyType;
import com.buer.common.oss.factory.OssFactory;
import com.buer.resource.api.dto.OssFileGroupDTO;
import com.buer.resource.api.dto.OssUploadDTO;
import com.buer.resource.api.entity.SysOss;
import com.buer.resource.api.query.OssQuery;
import com.buer.resource.api.vo.OssVO;
import com.buer.resource.biz.mapper.SysOssMapper;
import com.buer.resource.biz.service.SysOssService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.buer.resource.api.entity.table.SysOssTableDef.SYS_OSS;

/**
 * OSS对象存储表 ServiceImpl
 *
 * @author zoulan
 * @since 2023-08-27
 */
@Service
@RequiredArgsConstructor
public class SysOssServiceImpl extends ServiceImpl<SysOssMapper, SysOss> implements SysOssService {

    /**
     * 通过id查询
     *
     * @param id id
     * @return OssVO
     */
    @Override
    public OssVO getOssById(Long id) {
        return queryChain().and(SYS_OSS.ID.eq(id))
            .oneAs(OssVO.class);
    }

    /**
     * 上传文件
     *
     * @param ossUploadDTO 文件上传DTO
     * @return OssVO
     */
    @Override
    public OssVO upload(OssUploadDTO ossUploadDTO) {
        MultipartFile file = ossUploadDTO.getFile();
        String originalFilename = file.getOriginalFilename();
        String suffix = FileUtil.getSuffix(originalFilename);
        OssClient storage = OssFactory.instance();
        UploadResult uploadResult;
        try {
            uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType());
        } catch (IOException e) {
            throw new CheckException("上传失败", e);
        }

        // 保存文件信息
        SysOss oss = new SysOss();
        oss.setUrl(uploadResult.getUrl())
            .setFileSuffix(suffix)
            .setFileName(uploadResult.getFilename())
            .setBucketName(storage.getOssConfig()
                .getBucketName())
            .setOriginalName(originalFilename)
            .setService(storage.getConfigKey())
            .setFileSize(file.getSize())
            .setSort(0d);
        save(oss);
        OssVO ossVO = new OssVO();
        BeanUtil.copyProperties(oss, ossVO);
        return this.matchingUrl(ossVO);
    }

    /**
     * 下载文件
     *
     * @param ossId    ossId
     * @param response 输出内容
     */
    @Override
    public void download(Long ossId, HttpServletResponse response) throws IOException {
        OssVO sysOss = getOssById(ossId);
        if (ObjectUtil.isNull(sysOss)) {
            throw new CheckException("文件数据不存在!");
        }
        FileUtils.setAttachmentResponseHeader(response, sysOss.getOriginalName());
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE + "; charset=UTF-8");
        OssClient storage = OssFactory.instance();
        try (InputStream inputStream = storage.getObjectContent(sysOss.getUrl())) {
            int available = inputStream.available();
            IoUtil.copy(inputStream, response.getOutputStream(), available);
            response.setContentLength(available);
        } catch (Exception e) {
            throw new CheckException(e.getMessage());
        }
    }

    /**
     * 更新文件组
     *
     * @param entity OssFileGroupDTO
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean updateFileGroup(OssFileGroupDTO entity) {
        String ossIds = entity.getIds();
        Long fileGroupId = entity.getFileGroupId();
        OssClient storage = OssFactory.instance();

        // 假如没有ossIds，删除FileGroupId下的所有文件
        if (StrUtil.isBlank(ossIds)) {
            List<SysOss> deleteOssList = list(QueryWrapper.create()
                .eq(SysOss::getFileGroupId, fileGroupId));
            deleteOssList.forEach(oss -> storage.delete(oss.getUrl()));
            remove(QueryWrapper.create()
                .eq(SysOss::getFileGroupId, fileGroupId));
            return true;
        }
        List<String> ossIdList = StrUtil.split(ossIds, ",");

        // 删除多余文件
        List<SysOss> deleteOssList = list(QueryWrapper.create()
            .eq(SysOss::getFileGroupId, fileGroupId)
            .notIn(SysOss::getId, ossIdList));
        if (!deleteOssList.isEmpty()) {
            deleteOssList.forEach(oss -> storage.delete(oss.getUrl()));
            remove(QueryWrapper.create()
                .eq(SysOss::getFileGroupId, fileGroupId)
                .notIn(SysOss::getId, ossIdList));
        }

        // 更新文件成正式、排序
        List<SysOss> ossList = new ArrayList<>();
        for (int i = 0; i < ossIdList.size(); i++) {
            ossList.add(new SysOss()
                .setId(Long.parseLong(ossIdList.get(i)))
                .setFileGroupId(entity.getFileGroupId())
                .setIsTemp("0")
                .setSort((double) i)
            );
        }
        if (!ossList.isEmpty()) {
            updateBatch(ossList);
        }

        return true;
    }

    /**
     * 更新文件组列表
     *
     * @param ossFileGroupDTOList List<OssFileGroupDTO>
     * @return Boolean
     */
    @Override
    public boolean updateFileGroupList(List<OssFileGroupDTO> ossFileGroupDTOList) {
        ossFileGroupDTOList.forEach(this::updateFileGroup);
        return true;
    }

    /**
     * 通过id列表删除文件
     *
     * @param ossIds 文件id列表
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean removeOssByIds(List<Long> ossIds) {
        // List<String> idList = U.getIdListByStringWithEmptyThrow(ossIds, "文件Id");
        return removeByIds(ossIds);
    }

    /**
     * 通过fileGroupId 删除文件
     *
     * @param fileGroupId 文件组id,多个逗号隔开
     */
    @Override
    @Transactional
    public boolean removeOssByFileGroupId(String fileGroupId) {
        List<String> fileGroupIdList = StrUtil.split(fileGroupId, ",");
        List<SysOss> ossList = list(QueryWrapper.create()
            .in(SysOss::getFileGroupId, fileGroupIdList));
        OssClient storage = OssFactory.instance();
        for (SysOss oss : ossList) {
            storage.delete(oss.getUrl());
        }
        removeByIds(ossList);
        return false;
    }

    /**
     * 列表查询文件
     *
     * @param entity 查询对象
     * @return 列表
     */
    @Override
    public List<OssVO> listOss(OssQuery entity) {
        return getQueryChainByQuery(entity).listAs(OssVO.class);
    }

    /**
     * 分页查询文件
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Override
    public Page<OssVO> pageOss(Page<OssVO> page, OssQuery entity) {
        return getQueryChainByQuery(entity).pageAs(page, OssVO.class);
    }

    /**
     * 获取QueryChain对象
     */
    private QueryChain<SysOss> getQueryChainByQuery(OssQuery entity) {
        return queryChain()
            .and(SYS_OSS.FILE_GROUP_ID.eq(entity.getFileGroupId()));
    }

    /**
     * 匹配Url
     *
     * @param oss OSS对象
     * @return oss 匹配Url的OSS对象
     */
    private OssVO matchingUrl(OssVO oss) {
        OssClient storage = OssFactory.instance(oss.getService());
        // 仅修改桶类型为 private 的URL，临时URL时长为120s
        if (AccessPolicyType.PRIVATE == storage.getAccessPolicy()) {
            oss.setUrl(storage.getPrivateUrl(oss.getFileName(), 120));
        }
        return oss;
    }

}