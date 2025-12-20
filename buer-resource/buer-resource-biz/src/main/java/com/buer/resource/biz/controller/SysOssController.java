package com.buer.resource.biz.controller;

import cn.hutool.core.util.ObjectUtil;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.resource.api.dto.OssFileGroupDTO;
import com.buer.resource.api.dto.OssUploadDTO;
import com.buer.resource.api.query.OssQuery;
import com.buer.resource.api.vo.OssVO;
import com.buer.resource.biz.service.SysOssService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件 Controller
 *
 * @author zoulan
 * @since 2023-08-27
 */
@RestController
@RequestMapping("oss")
@RequiredArgsConstructor
@Tag(name = "文件")
public class SysOssController {

    private final SysOssService service;

    /**
     * 通过id查询文件
     *
     * @param id 文件id
     * @return OssVO
     */
    @Operation(summary = "通过id查询文件")
    @GetMapping(value = "/{id}")
    public R<OssVO> getOssById(@PathVariable("id") Long id) {
        return R.ok(service.getOssById(id));
    }

    /**
     * 上传OSS文件
     *
     * @param ossUploadDTO 文件上传DTO
     */
    @Operation(summary = "上传OSS文件")
    @SysLog("上传OSS文件")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<Map<String, String>> uploadOss(@ModelAttribute OssUploadDTO ossUploadDTO) {
        if (ObjectUtil.isNull(ossUploadDTO.getFile())) {
            return R.fail("上传文件不能为空");
        }
        OssVO oss = service.upload(ossUploadDTO);
        Map<String, String> map = new HashMap<>(2);
        map.put("url", oss.getUrl());
        map.put("fileName", oss.getOriginalName());
        map.put("ossId", oss.getId()
            .toString());
        return R.ok(map);
    }

    /**
     * 下载OSS文件
     *
     * @param ossId OSS对象ID
     */
    @Operation(summary = "下载OSS文件")
    @SysLog("下载OSS文件")
    @GetMapping("/download/{ossId}")
    public void download(@PathVariable Long ossId, HttpServletResponse response) throws IOException {
        service.download(ossId, response);
    }

    /**
     * 更新文件组
     *
     * @param entity OssFileGroupDTO
     * @return Boolean
     */
    @Operation(summary = "更新文件组")
    @SysLog("更新文件组")
    @PutMapping("/file-group")
    public R<Boolean> updateFileGroup(@RequestBody OssFileGroupDTO entity) {
        return R.ok(service.updateFileGroup(entity));
    }

    /**
     * 更新文件组列表
     *
     * @param ossFileGroupDTOList List<OssFileGroupDTO>
     * @return Boolean
     */
    @Operation(summary = "更新文件组列表")
    @SysLog("更新文件组列表")
    @PostMapping("/file-group-list")
    public R<Boolean> updateFileGroups(@RequestBody List<OssFileGroupDTO> ossFileGroupDTOList) {
        return R.ok(service.updateFileGroupList(ossFileGroupDTOList));
    }

    /**
     * 通过id删除文件
     *
     * @param ossIds 文件id列表
     * @return Boolean
     */
    @Operation(summary = "通过id删除文件")
    @SysLog("通过id删除文件")
    @DeleteMapping
    public R<Boolean> removeOssById(@RequestBody List<Long> ossIds) {
        return R.ok(service.removeOssByIds(ossIds));
    }

    /**
     * 通过fileGroupId删除文件
     *
     * @param fileGroupId 文件组id,多个逗号隔开
     * @return Boolean
     */
    @Operation(summary = "通过fileGroupId删除文件")
    @SysLog("通过fileGroupId删除文件")
    @DeleteMapping(value = "/file-group-id/{fileGroupId}")
    public R<Boolean> deleteOssByFileGroupId(@PathVariable String fileGroupId) {
        return R.ok(service.removeOssByFileGroupId(fileGroupId));
    }

    /**
     * 列表查询文件
     *
     * @param entity 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询文件")
    @GetMapping(value = "/list")
    public R<List<OssVO>> listOss(OssQuery entity) {
        return R.ok(service.listOss(entity));
    }

    /**
     * 分页查询文件
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询文件")
    @GetMapping(value = "/page")
    public R<Page<OssVO>> pageOss(Page<OssVO> page, OssQuery entity) {
        return R.ok(service.pageOss(page, entity));
    }

}