package com.buer.system.api.filler;

import com.buer.common.core.util.IdToLabelFiller;
import com.buer.system.api.feign.RemoteUserService;
import com.buer.system.api.vo.UserLabelVO;
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
 * UserLabelFiller
 * <p>
 * 功能：
 * 1. 单字段回填（name 或其他单字段）
 * 2. 多字段回填 DTO（如 name + email + deptName）
 * 3. 支持多列 ID / 逗号分隔 ID
 * 4. 内部统一批量调用 Feign
 *
 * @author zoulan
 * @since 2025-09-10
 */
@Component
@RequiredArgsConstructor
public class UserLabelFiller {

    private final RemoteUserService remoteUserService;

    /**
     * 单字段回填用户信息
     *
     * @param list     待回填对象列表
     * @param idGetter 从业务对象获取用户 ID（单值或逗号分隔、多列）
     * @param setter   回填字段 setter（如 setUserName）
     *                 <p>
     *                 流程：
     *                 1. 调用 IdToLabelFiller 提取所有 ID
     *                 2. 批量调用 Feign 获取 DTO Map
     *                 3. 遍历对象匹配 ID，将 label 拼接后回填业务对象字段
     */
    public <T> void fillField(List<T> list,
                              Function<T, Object> idGetter,
                              BiConsumer<T, String> setter) {
        IdToLabelFiller.chain(list, this::fetchUserMap)
            .field(idGetter, setter, UserLabelVO::getNickname)
            .fill();
    }

    /**
     * 多字段回填 DTO
     *
     * @param list  待回填对象列表
     * @param rules 可传多个 Pair<idGetter, setter>
     *              - idGetter: 获取业务对象中的 ID
     *              - setter: 回填整个 DTO，可设置多个字段
     *              <p>
     *              流程：
     *              1. 遍历规则收集所有 ID
     *              2. 批量调用 Feign 获取 Map<id, DTO>
     *              3. 遍历对象和规则，将 DTO 回填到对象字段
     *              4. 支持多列 ID / 逗号分隔
     */
    @SafeVarargs
    public final <T> void fillMulti(List<T> list,
                                    Pair<Function<T, Object>, BiConsumer<T, UserLabelVO>>... rules) {
        // 创建链式构造器
        IdToLabelFiller.ChainableBuilder<T, UserLabelVO> builder =
            IdToLabelFiller.chain(list, this::fetchUserMap);

        // 遍历规则，依次添加多字段回填
        for (Pair<Function<T, Object>, BiConsumer<T, UserLabelVO>> rule : rules) {
            builder.multiField(rule.getLeft(), rule.getRight());
        }

        // 执行回填
        builder.fill();
    }

    /**
     * 私有方法：批量查询用户 DTO
     *
     * @param ids 待查询 ID 集合
     * @return Map<String, UserDTO> key 为 ID 字符串，value 为 DTO
     * <p>
     * 流程：
     * 1. 将 String ID 转 Long 调用 Feign 批量查询
     * 2. 转成 Map<Long, UserDTO>
     * 3. 再转成 Map<String, UserDTO>，便于与业务对象 ID 对应
     */
    private Map<String, UserLabelVO> fetchUserMap(Set<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Long, UserLabelVO> map = remoteUserService.listUserLabelByIds(
                ids.stream()
                    .map(Long::valueOf)
                    .collect(Collectors.toList())
            )
            .getData()
            .stream()
            .collect(Collectors.toMap(UserLabelVO::getId, u -> u));

        return map.entrySet()
            .stream()
            .collect(Collectors.toMap(e -> String.valueOf(e.getKey()), Map.Entry::getValue));
    }
}

