package com.buer.common.mybatis.listener;

import com.buer.common.core.entity.SuperEntity;
import com.buer.common.security.util.SecurityUtils;
import com.mybatisflex.annotation.UpdateListener;

import java.time.LocalDateTime;

public class MybatisUpdateListener implements UpdateListener {


    @Override
    public void onUpdate(Object entity) {
        LocalDateTime now = LocalDateTime.now();
        String userId = SecurityUtils.getUserIdAsString();
        SuperEntity superEntity = (SuperEntity) entity;
        //设置 superEntity 被新增时的一些默认数据
        superEntity.setUpdateTime(now)
                .setUpdateUser(userId);
    }
}