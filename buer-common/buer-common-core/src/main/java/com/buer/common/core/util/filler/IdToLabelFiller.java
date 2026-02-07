package com.buer.common.core.util.filler;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * 通用 ID → Label 回填工具类
 *
 * 功能：
 * 1. 支持单字段回填（拼接字符串）
 * 2. 支持多字段回填 DTO（列表或多个字段）
 * 3. 支持多列 ID / 逗号分隔 ID
 * 4. 链式调用，业务层调用灵活简洁
 *
 * @author zoulan
 * @since 2025-09-10
 */
public class IdToLabelFiller {

    /**
     * 启动链式构造
     *
     * @param list 待处理业务对象列表
     * @param batchFetcher 批量查询函数，输入 ID 集合，返回 Map<id, DTO>
     * @param <T> 业务对象类型
     * @param <R> 批量查询 DTO 类型
     * @return 链式构造器
     */
    public static <T, R> ChainableBuilder<T, R> chain(List<T> list,
                                                       Function<Set<String>, Map<String, R>> batchFetcher) {
        return new ChainableBuilder<>(list, batchFetcher);
    }

    /**
     * 从各种格式的 ID 中提取标准化 List<String>
     * <p>
     * 支持：
     * - Long / Integer / Number → 转 String
     * - Collection / 数组 → 遍历转 String
     * - 逗号分隔字符串 → 拆分
     * - 空值过滤
     *
     * @param idVal 待解析的 ID 值
     * @return 标准化后的字符串 ID 列表
     */
    public static List<String> extractIds(Object idVal) {
        if (idVal == null) {
            return List.of();
        }

        // Number 类型直接转换
        if (idVal instanceof Number n) {
            return List.of(String.valueOf(n.longValue()));
        }

        // Collection 类型
        if (idVal instanceof Collection<?> coll) {
            return coll.stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        }

        // 数组类型
        if (idVal.getClass().isArray()) {
            return Arrays.stream((Object[]) idVal)
                .filter(Objects::nonNull)
                .map(Object::toString)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        }

        // 字符串类型（支持逗号分隔）
        String str = idVal.toString().trim();
        if (str.contains(",")) {
            return Arrays.stream(str.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        }

        return List.of(str);
    }

    /**
     * 链式构造器
     *
     * @param <T> 业务对象类型
     * @param <R> 批量查询 DTO 类型
     */
    public static class ChainableBuilder<T, R> {

        private final List<T> list;
        private final Function<Set<String>, Map<String, R>> batchFetcher;
        private final List<Rule<T, R>> rules = new ArrayList<>();

        private ChainableBuilder(List<T> list, Function<Set<String>, Map<String, R>> batchFetcher) {
            this.list = list;
            this.batchFetcher = batchFetcher;
        }

        /**
         * 添加单字段回填规则
         *
         * @param idGetter 从业务对象获取 ID
         * @param fieldSetter 回填字段 setter
         * @param dtoGetter 从 DTO 中获取 label 字段
         * @return 当前构造器
         */
        public ChainableBuilder<T, R> field(Function<T, Object> idGetter,
                                            BiConsumer<T, String> fieldSetter,
                                            Function<R, String> dtoGetter) {
            rules.add(new Rule<>(idGetter, (obj, matched) -> {
                String joined = matched.stream()
                    .map(dtoGetter)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(","));
                fieldSetter.accept(obj, joined);
            }));
            return this;
        }

        /**
         * 添加多字段回填规则
         *
         * @param idGetter 从业务对象获取 ID
         * @param mappers 多个 setter，可回填 DTO 多字段
         * @return 当前构造器
         */
        @SafeVarargs
        public final ChainableBuilder<T, R> multiField(Function<T, Object> idGetter,
                                                        BiConsumer<T, R>... mappers) {
            rules.add(new Rule<>(idGetter, (obj, matched) -> {
                // 保持原有逻辑：对每个 mapper，遍历所有 matched dto
                for (BiConsumer<T, R> mapper : mappers) {
                    matched.forEach(dto -> mapper.accept(obj, dto));
                }
            }));
            return this;
        }

        /**
         * 执行回填逻辑
         * <p>
         * 流程：
         * 1. 收集所有 ID（去重 + 保持顺序）
         * 2. 调用 batchFetcher 批量查询 DTO
         * 3. 遍历业务对象，匹配 ID 并执行规则回填
         */
        public void fill() {
            if (list == null || list.isEmpty() || rules.isEmpty()) {
                return;
            }

            Set<String> allIds = collectAllIds();
            if (allIds.isEmpty()) {
                return;
            }

            Map<String, R> resultMap = batchFetcher.apply(allIds);
            if (resultMap.isEmpty()) {
                return;
            }

            // 遍历业务对象和规则，执行回填
            for (T obj : list) {
                for (Rule<T, R> rule : rules) {
                    List<String> ids = extractIds(rule.idGetter.apply(obj));
                    if (ids.isEmpty()) {
                        continue;
                    }

                    // 匹配 DTO
                    List<R> matched = ids.stream()
                        .map(resultMap::get)
                        .filter(Objects::nonNull)
                        .toList();
                    if (matched.isEmpty()) {
                        continue;
                    }

                    // 执行回填
                    rule.filler.accept(obj, matched);
                }
            }
        }

        /** 收集所有 ID */
        private Set<String> collectAllIds() {
            LinkedHashSet<String> all = new LinkedHashSet<>();
            for (T obj : list) {
                for (Rule<T, R> rule : rules) {
                    all.addAll(extractIds(rule.idGetter.apply(obj)));
                }
            }
            return all;
        }
    }

    /**
     * 内部规则类 - 统一单字段和多字段处理
     */
    private static class Rule<T, R> {
        final Function<T, Object> idGetter;
        final BiConsumer<T, List<R>> filler;

        Rule(Function<T, Object> idGetter, BiConsumer<T, List<R>> filler) {
            this.idGetter = idGetter;
            this.filler = filler;
        }
    }
}
