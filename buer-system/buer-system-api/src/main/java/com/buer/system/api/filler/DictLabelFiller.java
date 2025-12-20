package com.buer.system.api.filler;

import com.buer.common.core.util.IdToLabelFiller;
import com.buer.system.api.feign.RemoteDictService;
import com.buer.system.api.vo.DictItemLabelVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * DictLabelFiller
 * <p>
 * 功能：
 * 1. 单字段回填（name + theme）
 * 2. 多字段回填 DTO（自定义回填逻辑）
 * 3. 支持多字典 key
 * 4. 内部统一批量调用 Feign
 *
 * @author zoulan
 * @since 2025-09-11
 */
@Component
@RequiredArgsConstructor
public class DictLabelFiller {

    private final RemoteDictService remoteDictService;

    /**
     * 单字段回填字典信息（name + theme）
     *
     * @param list        待回填对象列表
     * @param dictKey     字典 key（如 "orderType"）
     * @param idGetter    从业务对象获取字典值
     * @param nameSetter  回填名称 setter（如 setOrderTypeName）
     * @param themeSetter 回填主题 setter（如 setOrderTypeTheme）
     */
    public <T> void fillField(List<T> list,
                              String dictKey,
                              Function<T, Object> idGetter,
                              BiConsumer<T, String> nameSetter,
                              BiConsumer<T, String> themeSetter) {

        IdToLabelFiller.chain(list, ids -> fetchDictMap(Set.of(dictKey)))
            .multiField(obj -> wrapWithDictKey(dictKey, idGetter.apply(obj)),
                (t, dto) -> {
                    if (nameSetter != null) {
                        nameSetter.accept(t, dto.getName());
                    }
                    if (themeSetter != null) {
                        themeSetter.accept(t, dto.getTheme());
                    }
                })
            .fill();
    }

    /**
     * 多字段回填
     *
     * @param list  待回填对象列表
     * @param rules 可传多个 Triple<idGetter, dictKey, mapper>
     *              - idGetter: 获取业务对象中的字典值
     *              - dictKey: 字典类型 key（如 "orderType"）
     *              - mapper: 回填整个 DTO，可设置多个字段
     */
    @SafeVarargs
    public final <T> void fillMulti(List<T> list,
                                    Triple<Function<T, Object>, String, BiConsumer<T, DictItemLabelVO>>... rules) {

        // 收集所有 dictKey
        Set<String> dictKeys = Arrays.stream(rules)
            .map(Triple::getMiddle)
            .collect(Collectors.toSet());

        IdToLabelFiller.ChainableBuilder<T, DictItemLabelVO> builder =
            IdToLabelFiller.chain(list, ids -> fetchDictMap(dictKeys));

        for (Triple<Function<T, Object>, String, BiConsumer<T, DictItemLabelVO>> rule : rules) {
            builder.multiField(obj -> wrapWithDictKey(rule.getMiddle(), rule.getLeft()
                .apply(obj)), rule.getRight());
        }

        builder.fill();
    }

    /**
     * 拼接 dictKey:value，保证唯一性
     */
    private String wrapWithDictKey(String dictKey, Object value) {
        if (value == null) {
            return null;
        }
        return dictKey + ":" + value.toString();
    }

    /**
     * 批量查询字典数据
     *
     * @param dictKeys 多个字典 key
     * @return Map<dictKey:value, DictItemLabelVO>
     */
    private Map<String, DictItemLabelVO> fetchDictMap(Set<String> dictKeys) {
        if (dictKeys == null || dictKeys.isEmpty()) {
            return Collections.emptyMap();
        }

        // 调用远程字典服务（假设支持一次传多个 key）
        List<DictItemLabelVO> list = remoteDictService.listDictLabelByDictKeys(List.copyOf(dictKeys))
            .getData();

        return list.stream()
            .collect(Collectors.toMap(
                item -> item.getDictKey() + ":" + item.getValue(),
                item -> item
            ));
    }
}
