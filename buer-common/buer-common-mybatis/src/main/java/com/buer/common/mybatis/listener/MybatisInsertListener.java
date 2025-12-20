package com.buer.common.mybatis.listener;

import com.buer.common.core.entity.SuperEntity;
import com.buer.common.security.util.SecurityUtils;
import com.mybatisflex.annotation.InsertListener;

import java.time.LocalDateTime;

public class MybatisInsertListener implements InsertListener {

    @Override
    public void onInsert(Object entity) {
        LocalDateTime now = LocalDateTime.now();
        String userId = SecurityUtils.getUserIdAsString();
        SuperEntity superEntity = (SuperEntity) entity;
        //设置 superEntity 被新增时的一些默认数据
        superEntity.setCreateTime(now)
                .setCreateUser(userId);
        //多租户的场景下，设置当前 租户 ID ..
        //superEntity.setTenantId("....");
    }
}