package com.buer.system.biz.service.impl;

import com.buer.common.core.constant.CacheConstants;
import com.buer.common.core.util.StringUtils;
import com.buer.common.redis.util.CacheUtils;
import com.buer.system.api.entity.SysDict;
import com.buer.system.api.entity.SysDictItem;
import com.buer.system.api.query.DictQuery;
import com.buer.system.api.vo.DictItemVO;
import com.buer.system.api.vo.DictVO;
import com.buer.system.biz.mapper.SysDictMapper;
import com.buer.system.biz.service.SysDictItemService;
import com.buer.system.biz.service.SysDictService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.buer.system.api.entity.table.SysDictItemTableDef.SYS_DICT_ITEM;
import static com.buer.system.api.entity.table.SysDictTableDef.SYS_DICT;

/**
 * 字典 ServiceImpl
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    private final SysDictItemService sysDictItemService;

    /**
     * 通过id查询字典
     *
     * @param id id
     * @return DictVO
     */
    @Override
    public DictVO getDictById(Long id) {
        return getQueryChain().and(SYS_DICT.ID.eq(id))
            .oneAs(DictVO.class);
    }

    /**
     * 新增字典
     *
     * @param dict dict
     * @return Boolean
     */
    @Override
    public boolean addDict(SysDict dict) {
        return save(dict);
    }

    /**
     * 通过id修改字典
     *
     * @param dict dict
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean updateDict(SysDict dict) {
        SysDict oldDict = getById(dict.getId());
        CacheUtils.evict(CacheConstants.DICT, oldDict.getDictKey());
        if (!oldDict.getDictKey()
            .equals(dict.getDictKey())) {
            CacheUtils.evict(CacheConstants.DICT, dict.getDictKey());
            UpdateChain.of(SysDictItem.class)
                .set(SysDictItem::getDictKey, dict.getDictKey())
                .eq(SysDictItem::getDictKey, oldDict.getDictKey())
                .update();
        }
        return updateById(dict);
    }

    /**
     * 通过id删除字典
     *
     * @param dictIds dictIds
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean removeDictByIds(List<Long> dictIds) {
        List<SysDict> sysDictList = list(QueryWrapper.create()
            .and(SYS_DICT.ID.in(dictIds)));
        // 根据dictKeys,删除字典项
        List<String> dictKeys = sysDictList.stream()
            .map(SysDict::getDictKey)
            .collect(Collectors.toList());
        removeByIds(dictIds);
        dictIds.forEach(id -> CacheUtils.evict(CacheConstants.DICT, id));
        sysDictItemService.remove(QueryWrapper.create()
            .and(SYS_DICT_ITEM.DICT_KEY.in(dictKeys)));
        return true;
    }

    /**
     * 列表查询字典
     *
     * @param dictQuery 查询对象
     * @return 列表
     */
    @Override
    public List<DictVO> listDict(DictQuery dictQuery) {
        return getQueryChainByQuery(dictQuery).listAs(DictVO.class);
    }

    /**
     * 分页查询字典
     *
     * @param page      分页对象
     * @param dictQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<DictVO> pageDict(Page<DictVO> page, DictQuery dictQuery) {
        return getQueryChainByQuery(dictQuery).pageAs(page, DictVO.class);
    }

    /**
     * 获取QueryChain对象
     */
    private QueryChain<SysDict> getQueryChain() {
        return queryChain().select(SYS_DICT.ALL_COLUMNS);
    }

    /**
     * 通过query获取QueryChain对象
     */
    private QueryChain<SysDict> getQueryChainByQuery(DictQuery dictQuery) {
        return getQueryChain()
            .and(SYS_DICT.ID.in(StringUtils.arrayBySplit(dictQuery.getDictIds())))
            .and(SYS_DICT.DICT_KEY.like(dictQuery.getDictKey()))
            .and(SYS_DICT.NAME.like(dictQuery.getName()))
            .orderBy(SYS_DICT.CREATE_TIME.desc());
    }

    /**
     * 刷新字典缓存
     */
    @Override
    public void resetDictCache() {
        CacheUtils.clear(CacheConstants.DICT);
        List<DictVO> configs = listDict(new DictQuery());
        List<DictItemVO> configItems = sysDictItemService.listDictItem();
        configs.forEach(config -> {
            List<DictItemVO> configItems2 = configItems.stream()
                .filter(configItem -> configItem.getDictKey()
                    .equals(config.getDictKey()))
                .toList();
            CacheUtils.put(CacheConstants.DICT, config.getDictKey(), configItems2);
        });
    }

}