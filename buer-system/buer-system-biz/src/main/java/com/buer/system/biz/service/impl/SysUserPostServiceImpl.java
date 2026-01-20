package com.buer.system.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.buer.common.core.constant.CommonConstants;
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

import java.util.ArrayList;
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

    /**
     * 回填用户岗位信息
     *
     * @param users 用户列表
     */
    @Override
    public void fillUsersPostInfo(List<UserVO> users) {
        fillUsersPostInfo(users, UserVO::getId, UserVO::setPostIds, (u, names) -> u.setPostNames(names));
    }

    /**
     * 回填用户导出岗位信息
     *
     * @param users 用户导出列表
     */
    @Override
    public void fillUsersPostInfoForExport(List<UserExportVO> users) {
        fillUsersPostInfo(users, u -> StrUtil.isNotBlank(u.getId()) ? Long.valueOf(u.getId()) : null, null, (u, names) -> u.setPostNames(names));
    }

    private <T> void fillUsersPostInfo(List<T> users, Function<T, Long> getIdFunc, BiConsumer<T, String> setPostIdsFunc, BiConsumer<T, String> setPostNamesFunc) {
        if (CollUtil.isEmpty(users)) {
            return;
        }
        // 获取用户ID列表
        Set<Long> userIds = users.stream().map(getIdFunc).filter(Objects::nonNull).collect(Collectors.toSet());
        if (CollUtil.isEmpty(userIds)) {
            return;
        }
        // 分批查询岗位信息
        Map<Long, List<UserIdPostInfo>> postInfoMap = CollUtil.split(new ArrayList<>(userIds), CommonConstants.DB_QUERY_BATCH_SIZE).stream()
            .flatMap(batch -> queryChain()
                .select(SysUserPostTableDef.SYS_USER_POST.USER_ID.as("userId"), SysUserPostTableDef.SYS_USER_POST.POST_ID.as("postId"), SysPostTableDef.SYS_POST.NAME.as("postName"))
                .from(SysUserPostTableDef.SYS_USER_POST)
                .leftJoin(SysPostTableDef.SYS_POST).on(SysUserPostTableDef.SYS_USER_POST.POST_ID.eq(SysPostTableDef.SYS_POST.ID))
                .and(SysUserPostTableDef.SYS_USER_POST.USER_ID.in(batch))
                .listAs(UserIdPostInfo.class).stream())
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
        /**
         * 用户ID
         */
        private Long userId;
        /**
         * 岗位ID
         */
        private Long postId;
        /**
         * 岗位名称
         */
        private String postName;
    }
}


