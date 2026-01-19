package com.buer.system.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.buer.system.api.entity.SysUserPost;
import com.buer.system.api.entity.table.SysPostTableDef;
import com.buer.system.api.entity.table.SysUserPostTableDef;
import com.buer.system.api.vo.UserExportVO;
import com.buer.system.api.vo.UserVO;
import com.buer.system.biz.mapper.SysUserPostMapper;
import com.buer.system.biz.service.SysUserPostService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户岗位 ServiceImpl
 *
 * @author zoulan
 * @since 2023-05-07
 */
@Service
@RequiredArgsConstructor
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements SysUserPostService {

    @Override
    public void fillUsersPostInfo(List<UserVO> users) {
        fillUsersPostInfo(users, UserVO::getId, UserVO::setPostIds, (u, names) -> u.setPostNames(names));
    }

    @Override
    public void fillUsersPostInfoForExport(List<UserExportVO> users) {
        fillUsersPostInfo(users, u -> StrUtil.isNotBlank(u.getId()) ? Long.valueOf(u.getId()) : null, null, (u, names) -> u.setPostNames(names));
    }

    private <T> void fillUsersPostInfo(List<T> users, Function<T, Long> getIdFunc, BiConsumer<T, String> setPostIdsFunc, BiConsumer<T, String> setPostNamesFunc) {
        if (CollUtil.isEmpty(users)) {
            return;
        }
        Set<Long> userIds = users.stream().map(getIdFunc).filter(Objects::nonNull).collect(Collectors.toSet());
        if (CollUtil.isEmpty(userIds)) {
            return;
        }
        Map<Long, List<UserIdPostInfo>> postInfoMap = queryChain()
            .select(SysUserPostTableDef.SYS_USER_POST.USER_ID.as("userId"), SysUserPostTableDef.SYS_USER_POST.POST_ID.as("postId"), SysPostTableDef.SYS_POST.NAME.as("postName"))
            .from(SysUserPostTableDef.SYS_USER_POST)
            .leftJoin(SysPostTableDef.SYS_POST).on(SysUserPostTableDef.SYS_USER_POST.POST_ID.eq(SysPostTableDef.SYS_POST.ID))
            .and(SysUserPostTableDef.SYS_USER_POST.USER_ID.in(userIds))
            .listAs(UserIdPostInfo.class).stream()
            .collect(Collectors.groupingBy(UserIdPostInfo::getUserId));
        users.forEach(user -> {
            Long userId = getIdFunc.apply(user);
            if (userId != null) {
                List<UserIdPostInfo> infos = postInfoMap.getOrDefault(userId, List.of());
                if (setPostIdsFunc != null) {
                    setPostIdsFunc.accept(user, infos.stream().map(info -> String.valueOf(info.getPostId())).collect(Collectors.joining(StrUtil.COMMA)));
                }
                setPostNamesFunc.accept(user, infos.stream().map(UserIdPostInfo::getPostName).filter(Objects::nonNull).collect(Collectors.joining(StrUtil.COMMA)));
            }
        });
    }

    @Data
    private static class UserIdPostInfo {
        private Long userId;
        private Long postId;
        private String postName;
    }
}


