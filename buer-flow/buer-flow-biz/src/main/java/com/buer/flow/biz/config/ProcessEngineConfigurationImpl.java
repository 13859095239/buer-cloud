package com.buer.flow.biz.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 当 MyBatis-Flex Flowable 集成时，需要覆盖其自动配置
 * 添加 mybatis-flex 的事务管理器（FlexTransactionManager）和 DataSource（FlexDataSource） 注入到 ProcessEngineConfiguration
 * 详情见官网 https://mybatis-flex.com/
 *
 * @author zoulan
 * @since 2025-08-16
 */
public class ProcessEngineConfigurationImpl {

    @Bean
    public ProcessEngineConfiguration processEngineConfiguration(
        SqlSessionFactory sqlSessionFactory,
        PlatformTransactionManager annotationDrivenTransactionManager) {

        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();

        // 指定 MyBatis-Flex  数据源
        processEngineConfiguration.setDataSource(sqlSessionFactory.getConfiguration()
            .getEnvironment()
            .getDataSource());

        // 配置 MyBatis-Flex  的事务管理器
        processEngineConfiguration.setTransactionManager(annotationDrivenTransactionManager);
        return processEngineConfiguration;
    }
}
