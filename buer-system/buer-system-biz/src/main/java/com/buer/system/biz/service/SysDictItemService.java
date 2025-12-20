package com.buer.system.biz.service;

import com.buer.system.api.entity.SysDictItem;
import com.buer.system.api.query.DictItemQuery;
import com.buer.system.api.vo.DictItemLabelVO;
import com.buer.system.api.vo.DictItemVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 字典项 Service
 *
 * @author zoulan
 * @since 2023-05-06
 */
public interface SysDictItemService extends IService<SysDictItem> {

    /**
     * 通过id查询字典项
     *
     * @param id id
     * @return 字典项信息
     */
    DictItemVO getDictItemById(Long id);

    /**
     * 新增字典项
     *
     * @param dictItem 字典项对象
     * @return Boolean
     */
    boolean addDictItem(SysDictItem dictItem);

    /**
     * 通过id修改字典项
     *
     * @param dictItem 字典项对象
     * @return Boolean
     */
    boolean updateDictItem(SysDictItem dictItem);

    /**
     * 通过id删除字典项
     *
     * @param dictItemIds dictItemIds
     * @return Boolean
     */
    boolean removeDictItemByIds(List<Long> dictItemIds);

    /**
     * 列表查询字典项
     *
     * @return 列表
     */
    List<DictItemVO> listDictItem();

    /**
     * 通过key，列表查询字典项
     *
     * @param key key
     * @return 列表
     */
    List<DictItemVO> listDictItemByKey(String key);

    /**
     * 通过keys，列表查询字典项
     *
     * @param keys keys
     * @return 列表
     */
    List<DictItemVO> listDictItemByKeys(List<String> keys);

    /**
     * 列表查询，字典外键数据
     *
     * @param dictKeys 字典dictKeys列表
     * @return 列表
     */
    List<DictItemLabelVO> listDictItemLabelByDictKeys(List<String> dictKeys);

    /**
     * 分页查询字典项
     *
     * @param page          分页对象
     * @param dictItemQuery 查询对象
     * @return 分页对象
     */
    Page<DictItemVO> pageDictItem(Page<DictItemVO> page, DictItemQuery dictItemQuery);

}