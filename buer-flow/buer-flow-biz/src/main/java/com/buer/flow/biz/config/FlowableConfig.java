package com.buer.flow.biz.config;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowableConfig {
    @Bean
    public EngineConfigurationConfigurer<SpringProcessEngineConfiguration> customIdGeneratorConfigurer() {
        return configuration -> {
            configuration.setIdGenerator(new HutoolSnowflakeIdGenerator(1, 1));
        };
    }
}
