package com.buer.system.api.filler;

import com.buer.common.core.util.filler.AbstractLabelFiller;
import com.buer.system.api.feign.RemotePostService;
import com.buer.system.api.vo.PostLabelVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 岗位标签回填器
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
public class PostLabelFiller extends AbstractLabelFiller<PostLabelVO> {

    private final RemotePostService remotePostService;

    @Override
    protected Map<String, PostLabelVO> fetchLabelMap(Set<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Long, PostLabelVO> map = remotePostService.listPostLabelByIds(toLongList(ids))
            .getData()
            .stream()
            .collect(Collectors.toMap(PostLabelVO::getId, p -> p));

        return toStringKeyMap(map);
    }

    @Override
    protected Function<PostLabelVO, String> getNameGetter() {
        return PostLabelVO::getName;
    }
}


