package com.buer.system.api.filler;

import com.buer.common.core.util.filler.AbstractLabelFiller;
import com.buer.system.api.feign.RemoteUserService;
import com.buer.system.api.vo.UserLabelVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户标签回填器
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
public class UserLabelFiller extends AbstractLabelFiller<UserLabelVO> {

    private final RemoteUserService remoteUserService;

    @Override
    protected Map<String, UserLabelVO> fetchLabelMap(Set<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Long, UserLabelVO> map = remoteUserService.listUserLabelByIds(toLongList(ids))
            .getData()
            .stream()
            .collect(Collectors.toMap(UserLabelVO::getId, u -> u));

        return toStringKeyMap(map);
    }

    @Override
    protected Function<UserLabelVO, String> getNameGetter() {
        return UserLabelVO::getNickname;
    }
}

