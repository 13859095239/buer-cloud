package com.buer.system.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.buer.system.api.entity.SysUserPost;
import com.buer.system.api.entity.table.SysPostTableDef;
import com.buer.system.api.entity.table.SysUserPostTableDef;
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
     * 为传入的用户列表回填岗位信息
     *
     * @param users 用户列表
     *
     */
    @Override
    public void fillUsersPostInfo(List<UserVO> users) {
        if (CollUtil.isEmpty(users)) {
            return;
        }
        Set<Long> userIds = users.stream()
            .map(UserVO::getId)
            .collect(Collectors.toSet());

        // 一次联表查询：获取 userId, postId, postName
        List<UserIdPostInfo> rows = queryChain()
            .select(
                SysUserPostTableDef.SYS_USER_POST.USER_ID.as("userId"),
                SysUserPostTableDef.SYS_USER_POST.POST_ID.as("postId"),
                SysPostTableDef.SYS_POST.NAME.as("postName")
            )
            .from(SysUserPostTableDef.SYS_USER_POST)
            .leftJoin(SysPostTableDef.SYS_POST)
            .on(SysUserPostTableDef.SYS_USER_POST.POST_ID.eq(SysPostTableDef.SYS_POST.ID))
            .and(SysUserPostTableDef.SYS_USER_POST.USER_ID.in(userIds))
            .listAs(UserIdPostInfo.class);
        // 转成map对象
        Map<Long, List<UserIdPostInfo>> userIdToInfos = rows.stream()
            .collect(Collectors.groupingBy(UserIdPostInfo::getUserId));
        // 遍历用户列表，为每个用户回填岗位信息
        for (UserVO user : users) {
            List<UserIdPostInfo> infos = userIdToInfos.getOrDefault(user.getId(), List.of());
            String postIdsStr = infos.stream()
                .map(UserIdPostInfo::getPostId)
                .map(String::valueOf)
                .collect(Collectors.joining(StrUtil.COMMA));
            String postNamesStr = infos.stream()
                .map(UserIdPostInfo::getPostName)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(StrUtil.COMMA));
            user.setPostIds(postIdsStr);
            user.setPostNames(postNamesStr);
        }
    }

    @Data
    private static class UserIdPostInfo {
        private Long userId;
        private Long postId;
        private String postName;
    }
}


