package com.buer.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.buer.common.core.entity.R;
import com.buer.common.log.annotation.SysLog;
import com.buer.system.api.entity.SysDict;
import com.buer.system.api.entity.SysDictItem;
import com.buer.system.api.query.DictItemQuery;
import com.buer.system.api.query.DictQuery;
import com.buer.system.api.vo.DictItemLabelVO;
import com.buer.system.api.vo.DictItemVO;
import com.buer.system.api.vo.DictVO;
import com.buer.system.biz.service.SysDictItemService;
import com.buer.system.biz.service.SysDictService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典 Controller
 *
 * @author zoulan
 * @since 2023-05-06
 */
@RestController
@RequestMapping("dict")
@RequiredArgsConstructor
@Tag(name = "字典")
public class SysDictController {

    private final SysDictService service;

    private final SysDictItemService sysDictItemService;

    /**
     * 通过id查询字典
     *
     * @param id id
     * @return DictVO
     */
    @Operation(summary = "通过id查询字典")
    @GetMapping(value = "/{id}")
    public R<DictVO> getDictById(@PathVariable Long id) {
        return R.ok(service.getDictById(id));
    }

    /**
     * 新增字典
     *
     * @param entity SysDict
     * @return Boolean
     */
    @Operation(summary = "新增字典")
    @PostMapping
    @SaCheckPermission("dict-add")
    public R<Boolean> addDict(@RequestBody SysDict entity) {
        return R.ok(service.addDict(entity));
    }

    /**
     * 通过id修改字典
     *
     * @param entity SysDict
     * @return Boolean
     */
    @Operation(summary = "通过id修改字典")
    @PutMapping
    @SaCheckPermission("dict-edit")
    public R<Boolean> updateDict(@RequestBody SysDict entity) {
        return R.ok(service.updateDict(entity));
    }

    /**
     * 通过id删除字典
     *
     * @param dictIds dictIds
     * @return Boolean
     */
    @SysLog("删除字典")
    @Operation(summary = "通过id删除字典")
    @DeleteMapping
    @SaCheckPermission("dict-delete")
    public R<Boolean> removeDictByIds(@RequestBody List<Long> dictIds) {
        return R.ok(service.removeDictByIds(dictIds));
    }

    /**
     * 列表查询字典
     *
     * @param entity 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询字典")
    @GetMapping(value = "/list")
    public R<List<DictVO>> listDict(DictQuery entity) {
        return R.ok(service.listDict(entity));
    }

    /**
     * 分页查询字典
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询字典")
    @GetMapping(value = "/page")
    public R<Page<DictVO>> pageDict(Page<DictVO> page, DictQuery entity) {
        return R.ok(service.pageDict(page, entity));
    }

    /**
     * 通过id查询字典项
     *
     * @param id id
     * @return DictVO
     */
    @Operation(summary = "通过id查询字典项")
    @GetMapping(value = "/item/{id}")
    public R<DictItemVO> getDictItemById(@PathVariable Long id) {
        return R.ok(sysDictItemService.getDictItemById(id));
    }

    /**
     * 新增字典项
     *
     * @param entity
     * @return Boolean
     */
    @Operation(summary = "新增字典项")
    @PostMapping("/item")
    @SaCheckPermission("dict-add")
    public R<Boolean> saveDictItem(@RequestBody SysDictItem entity) {
        return R.ok(sysDictItemService.addDictItem(entity));
    }

    /**
     * 通过id修改字典项
     *
     * @param entity
     * @return Boolean
     */
    @Operation(summary = "通过id修改字典项")
    @PutMapping("/item")
    @SaCheckPermission("dict-edit")
    public R<Boolean> updateDictItem(@RequestBody SysDictItem entity) {
        return R.ok(sysDictItemService.updateDictItem(entity));
    }

    /**
     * 通过id删除字典项
     *
     * @param dictItemIds dictItemIds
     * @return Boolean
     */
    @Operation(summary = "通过id删除字典项")
    @DeleteMapping("/item")
    @SaCheckPermission("dict-delete")
    public R<Boolean> removeDictItemByIds(@RequestBody List<Long> dictItemIds) {
        return R.ok(sysDictItemService.removeDictItemByIds(dictItemIds));
    }


    /**
     * 通过key，列表查询字典项
     *
     * @param key key
     * @return 列表
     */
    @Operation(summary = "列表查询字典项")
    @GetMapping(value = "/item/list/key/{key}")
    public R<List<DictItemVO>> getDictItemListByKey(@PathVariable("key") String key) {
        return R.ok(sysDictItemService.listDictItemByKey(key));
    }

    /**
     * 列表查询，字典外键数据
     *
     * @param dictKeys 字典dictKeys列表
     * @return 列表
     */
    @Operation(summary = "列表查询，用户外键数据")
    @PostMapping(value = "/listDictLabelByDictKeys")
    public R<List<DictItemLabelVO>> listDictItemLabelByDictKeys(@RequestBody List<String> dictKeys) {
        List<DictItemLabelVO> users = sysDictItemService.listDictItemLabelByDictKeys(dictKeys);
        return R.ok(users);
    }

    /**
     * 通过keys，列表查询字典项
     *
     * @param keys keys
     * @return 列表
     */
    @Operation(summary = "列表查询字典项")
    @PostMapping(value = "/item/list/keys")
    public R<List<DictItemVO>> getDictItemListByKeys(@RequestBody List<String> keys) {
        return R.ok(sysDictItemService.listDictItemByKeys(keys));
    }

    /**
     * 分页查询字典项
     *
     * @param page   分页对象
     * @param entity 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询字典项")
    @GetMapping(value = "/item/page")
    public R<Page<DictItemVO>> pageDictItem(Page<DictItemVO> page, DictItemQuery entity) {
        return R.ok(sysDictItemService.pageDictItem(page, entity));
    }

    /**
     * 刷新字典缓存
     */
    @Operation(summary = "刷新字典缓存")
    @DeleteMapping("/refreshCache")
    @SaCheckPermission("dict-delete")
    public R refreshCache() {
        service.resetDictCache();
        return R.ok();
    }
}