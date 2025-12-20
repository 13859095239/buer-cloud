package com.buer.common.mybatis.config;

import com.buer.common.core.entity.SuperEntity;
import com.buer.common.mybatis.listener.MybatisInsertListener;
import com.buer.common.mybatis.listener.MybatisUpdateListener;
import com.buer.common.mybatis.tenant.MyTenantFactory;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.audit.ConsoleMessageCollector;
import com.mybatisflex.core.audit.MessageCollector;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.mybatisflex.core.logicdelete.LogicDeleteProcessor;
import com.mybatisflex.core.logicdelete.impl.IntegerLogicDeleteProcessor;
import com.mybatisflex.core.query.QueryColumnBehavior;
import com.mybatisflex.core.tenant.TenantFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * myBatisFlex 配置
 *
 * @author zoulan
 * @since 2025-04-18
 */
@Configuration
public class MyBatisFlexConfiguration {
    public MyBatisFlexConfiguration() {

        //开启审计功能
        AuditManager.setAuditEnable(true);

        //设置 SQL 审计收集器
        MessageCollector collector = new ConsoleMessageCollector();
        AuditManager.setMessageCollector(collector);

        // 自动填充字段
        MybatisInsertListener mybatisInsertListener = new MybatisInsertListener();
        MybatisUpdateListener mybatisUpdateListener = new MybatisUpdateListener();
        FlexGlobalConfig config = FlexGlobalConfig.getDefaultConfig();

        // 全局配置逻辑删除字段
        config.setLogicDeleteColumn("del_flag");
        // 设置数据库正常时的值
        config.setNormalValueOfLogicDelete("0");
        //设置数据已被删除时的值
        config.setDeletedValueOfLogicDelete("1");

        //全局配置乐观锁字段
        config.setVersionColumn("version");

        // 设置SuperEntity类时启用
        config.registerInsertListener(mybatisInsertListener, SuperEntity.class);
        config.registerUpdateListener(mybatisUpdateListener, SuperEntity.class);

        // 设置全局主键策略
        FlexGlobalConfig.KeyConfig keyConfig = new FlexGlobalConfig.KeyConfig();
        keyConfig.setKeyType(KeyType.Generator);
        keyConfig.setValue(KeyGenerators.snowFlakeId);

        FlexGlobalConfig.getDefaultConfig()
            .setKeyConfig(keyConfig);
        // 使用内置规则自动忽略 null 和 空字符串
        QueryColumnBehavior.setIgnoreFunction(QueryColumnBehavior.IGNORE_EMPTY);
        // 使用内置规则自动忽略 null 和 空白字符串
        QueryColumnBehavior.setIgnoreFunction(QueryColumnBehavior.IGNORE_BLANK);

        // 全局配置多租户字段
        config.setTenantColumn("tenant_id");
    }

    /**
     * 加载自定义租户处理器
     */
    @Bean
    public TenantFactory tenantFactory() {
        return new MyTenantFactory();
    }

    /**
     * 加载默认的逻辑删除处理器
     */
    @Bean
    public LogicDeleteProcessor logicDeleteProcessor() {
        return new IntegerLogicDeleteProcessor();
    }
}
