package com.buer.system.api.filler;

import com.buer.common.core.util.IdToLabelFiller;
import com.buer.system.api.feign.RemotePostService;
import com.buer.system.api.vo.PostLabelVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * PostLabelFiller
 * <p>
 * 功能：
 * 1. 单字段回填（岗位名称等单字段）
 * 2. 多字段回填 DTO（如 岗位名称 + 是否领导 等）
 * 3. 支持多列 ID / 逗号分隔 ID
 * 4. 内部统一批量调用 Feign
 *
 * @author zoulan
 * @since 2025-09-11
 */
@Component
@RequiredArgsConstructor
public class PostLabelFiller {

    private final RemotePostService remotePostService;

    /**
     * 单字段回填岗位信息
     *
     * @param list     待回填对象列表
     * @param idGetter 从业务对象获取岗位 ID（单值或逗号分隔、多列）
     * @param setter   回填字段 setter（如 setPostName）
     */
    public <T> void fillField(List<T> list,
                              Function<T, Object> idGetter,
                              BiConsumer<T, String> setter) {
        IdToLabelFiller.chain(list, this::fetchPostMap)
            .field(idGetter, setter, PostLabelVO::getName)
            .fill();
    }

    /**
     * 多字段回填 DTO
     *
     * @param list  待回填对象列表
     * @param rules 可传多个 Pair<idGetter, setter>
     *              - idGetter: 获取业务对象中的 ID
     *              - setter: 回填整个 DTO，可设置多个字段
     */
    @SafeVarargs
    public final <T> void fillMulti(List<T> list,
                                    Pair<Function<T, Object>, BiConsumer<T, PostLabelVO>>... rules) {
        IdToLabelFiller.ChainableBuilder<T, PostLabelVO> builder =
            IdToLabelFiller.chain(list, this::fetchPostMap);

        for (Pair<Function<T, Object>, BiConsumer<T, PostLabelVO>> rule : rules) {
            builder.multiField(rule.getLeft(), rule.getRight());
        }

        builder.fill();
    }

    /**
     * 私有方法：批量查询岗位 DTO
     *
     * @param ids 待查询 ID 集合
     * @return Map<String, PostVO> key 为 ID 字符串，value 为 DTO
     */
    private Map<String, PostLabelVO> fetchPostMap(Set<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Long, PostLabelVO> map = remotePostService.listPostLabelByIds(
                ids.stream()
                    .map(Long::valueOf)
                    .collect(Collectors.toList())
            )
            .getData()
            .stream()
            .collect(Collectors.toMap(PostLabelVO::getId, p -> p));

        return map.entrySet()
            .stream()
            .collect(Collectors.toMap(e -> String.valueOf(e.getKey()), Map.Entry::getValue));
    }
}


