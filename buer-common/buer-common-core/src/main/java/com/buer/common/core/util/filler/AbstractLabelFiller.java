package com.buer.common.core.util.filler;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 抽象标签回填基类
 * <p>
 * 提供通用的单字段和多字段回填方法，减少子类重复代码
 *
 * @param <V> 标签 VO 类型
 * @author zoulan
 * @since 2025-09-11
 */
public abstract class AbstractLabelFiller<V> {

    /**
     * 批量查询标签数据
     *
     * @param ids ID 集合（String 类型）
     * @return Map<String, V> key 为 ID 字符串，value 为标签 VO
     */
    protected abstract Map<String, V> fetchLabelMap(Set<String> ids);

    /**
     * 获取标签的显示名称字段
     *
     * @return 从标签 VO 中获取名称的 Function
     */
    protected abstract Function<V, String> getNameGetter();

    /**
     * 单字段回填
     *
     * @param list      待回填对象列表
     * @param idGetter  从业务对象获取 ID
     * @param setter    回填字段 setter
     * @param <T>       业务对象类型
     */
    public <T> void fillField(List<T> list,
                              Function<T, Object> idGetter,
                              BiConsumer<T, String> setter) {
        IdToLabelFiller.chain(list, this::fetchLabelMap)
            .field(idGetter, setter, getNameGetter())
            .fill();
    }

    /**
     * 多字段回填
     *
     * @param list  待回填对象列表
     * @param rules 可传多个 Pair<idGetter, setter>
     * @param <T>   业务对象类型
     */
    @SafeVarargs
    public final <T> void fillMulti(List<T> list,
                                   Pair<Function<T, Object>, BiConsumer<T, V>>... rules) {
        IdToLabelFiller.ChainableBuilder<T, V> builder =
            IdToLabelFiller.chain(list, this::fetchLabelMap);

        for (Pair<Function<T, Object>, BiConsumer<T, V>> rule : rules) {
            builder.multiField(rule.getLeft(), rule.getRight());
        }

        builder.fill();
    }

    /**
     * 工具方法：将 String ID 集合转换为 Long 列表
     */
    protected static List<Long> toLongList(Set<String> ids) {
        return ids.stream()
            .map(Long::valueOf)
            .collect(Collectors.toList());
    }

    /**
     * 工具方法：将 Map<Long, V> 转换为 Map<String, V>
     */
    protected static <V> Map<String, V> toStringKeyMap(Map<Long, V> longKeyMap) {
        return longKeyMap.entrySet().stream()
            .collect(Collectors.toMap(
                e -> String.valueOf(e.getKey()),
                Map.Entry::getValue
            ));
    }
}
