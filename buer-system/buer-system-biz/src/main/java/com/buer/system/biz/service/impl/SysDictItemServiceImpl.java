package com.buer.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.buer.common.core.constant.CacheConstants;
import com.buer.common.core.util.StringUtils;
import com.buer.common.redis.util.CacheUtils;
import com.buer.system.api.entity.SysDictItem;
import com.buer.system.api.query.DictItemQuery;
import com.buer.system.api.vo.DictItemLabelVO;
import com.buer.system.api.vo.DictItemVO;
import com.buer.system.biz.mapper.SysDictItemMapper;
import com.buer.system.biz.service.SysDictItemService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.buer.system.api.entity.table.SysDictItemTableDef.SYS_DICT_ITEM;

/**
 * 字典项 ServiceImpl
 *
 * @author zoulan
 * @since 2023-05-06
 */
@Service
@RequiredArgsConstructor
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService {

    /**
     * 通过id查询字典项
     *
     * @param id id
     * @return DictItemVO
     */
    @Override
    public DictItemVO getDictItemById(Long id) {
        return queryChain().and(SYS_DICT_ITEM.ID.eq(id))
            .oneAs(DictItemVO.class);
    }

    /**
     * 新增字典项
     *
     * @param dictItem 字典项对象
     * @return Boolean
     */
    @Override
    @CacheEvict(cacheNames = CacheConstants.DICT, key = "#dictItem.dictKey")
    public boolean addDictItem(SysDictItem dictItem) {
        return save(dictItem);
    }

    /**
     * 通过id修改字典项
     *
     * @param dictItem 字典项对象
     * @return Boolean
     */
    @Override
    @CacheEvict(cacheNames = CacheConstants.DICT, key = "#dictItem.dictKey")
    public boolean updateDictItem(SysDictItem dictItem) {
        return updateById(dictItem);
    }

    /**
     * 通过id删除字典项
     *
     * @param dictItemIds dictItemIds
     * @return Boolean
     */
    @Override
    @Transactional
    public boolean removeDictItemByIds(List<Long> dictItemIds) {
        // List<String> dictItemIdList = U.getIdListByStringWithEmptyThrow(dictItemIds, "字典项Id");
        removeByIds(dictItemIds);
        dictItemIds.forEach(id -> CacheUtils.evict(CacheConstants.DICT, id));
        return true;
    }

    /**
     * 列表查询字典项
     *
     * @return 列表
     */
    @Override
    public List<DictItemVO> listDictItem() {
        return BeanUtil.copyToList(
            list(),
            DictItemVO.class
        );
    }

    /**
     * 通过key，列表查询字典项
     *
     * @param key key
     * @return 列表
     */
    @Override
    @Cacheable(cacheNames = CacheConstants.DICT, key = "#key")
    public List<DictItemVO> listDictItemByKey(String key) {
        List<DictItemVO> dictItemVOs = BeanUtil.copyToList(
            list(QueryWrapper.create()
                .eq(SysDictItem::getDictKey, key)),
            DictItemVO.class
        );
        return dictItemVOs;
    }

    /**
     * 通过keys，列表查询字典项
     *
     * @param keys keys
     * @return 列表
     */
    @Override
    public List<DictItemVO> listDictItemByKeys(List<String> keys) {
        List<DictItemVO> dictItems = new ArrayList<>();
        keys.forEach(key -> {
                // 从缓存中获取
                List<DictItemVO> dictItemsCache = CacheUtils.get(CacheConstants.DICT, key);
                // 因相同类调用不会触发Cacheable,因此需要手动写缓存代码
                if (dictItemsCache == null) {
                    // 缓存不存在,从数据库中获取
                    List<DictItemVO> dictItemVOs = listDictItemByKey(key);
                    dictItems.addAll(dictItemVOs);
                    // 更新缓存
                    CacheUtils.put(CacheConstants.DICT, key, dictItemVOs);
                } else {
                    // 缓存存在,读取缓存
                    dictItems.addAll(dictItemsCache);
                }
            }
        );
        return dictItems;
    }

    /**
     * 列表查询，字典外键数据
     *
     * @param dictKeys 字典dictKeys列表
     * @return 列表
     */
    @Override
    public List<DictItemLabelVO> listDictItemLabelByDictKeys(List<String> dictKeys) {
        List<DictItemVO> dictItems = listDictItemByKeys(dictKeys);
        return dictItems.stream()
            .map(a -> {
                DictItemLabelVO b = new DictItemLabelVO();
                BeanUtil.copyProperties(a, b);
                return b;
            })
            .toList();
    }

    /**
     * 分页查询字典项
     *
     * @param page          分页对象
     * @param dictItemQuery 查询对象
     * @return 分页对象
     */
    @Override
    public Page<DictItemVO> pageDictItem(Page<DictItemVO> page, DictItemQuery dictItemQuery) {
        return getQueryChainByQuery(dictItemQuery).pageAs(page, DictItemVO.class);
    }

    /**
     * 获取QueryChain对象
     */
    private QueryChain<SysDictItem> getQueryChainByQuery(DictItemQuery entity) {
        return queryChain()
            .and(SYS_DICT_ITEM.DICT_KEY.like(entity.getDictKey()))
            .and(SYS_DICT_ITEM.DICT_KEY.in(StringUtils.arrayBySplit(entity.getDictKeys())))
            .orderBy(SYS_DICT_ITEM.SORT.asc());
    }
}