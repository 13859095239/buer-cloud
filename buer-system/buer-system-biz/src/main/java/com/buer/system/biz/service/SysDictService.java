package com.buer.system.biz.service;

import com.buer.system.api.entity.SysDict;
import com.buer.system.api.query.DictQuery;
import com.buer.system.api.vo.DictVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 字典 Service
 *
 * @author zoulan
 * @since 2023-05-06
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 通过id查询字典
     *
     * @param id id
     * @return 字典信息
     */
    DictVO getDictById(Long id);

    /**
     * 新增字典
     *
     * @param dict
     * @return Boolean
     */
    boolean addDict(SysDict dict);

    /**
     * 通过id修改字典
     *
     * @param dict
     * @return Boolean
     */
    boolean updateDict(SysDict dict);

    /**
     * 通过id删除字典
     *
     * @param dictIds dictIds
     * @return Boolean
     */
    boolean removeDictByIds(List<Long> dictIds);

    /**
     * 列表查询字典
     *
     * @param dictQuery 查询对象
     * @return 列表
     */
    List<DictVO> listDict(DictQuery dictQuery);

    /**
     * 分页查询字典
     *
     * @param page      分页对象
     * @param dictQuery 查询对象
     * @return 分页对象
     */
    Page<DictVO> pageDict(Page<DictVO> page, DictQuery dictQuery);

    /**
     * 刷新字典缓存
     */
    void resetDictCache();

}