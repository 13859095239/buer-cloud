package com.buer.system.biz.mq.consumer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class TestStreamConsumer {

    @Bean
    Consumer<String> demo() {
        log.info("初始化订阅");
        return msg -> {
            log.info("通过stream消费到消息 => {}", msg.toString());
        };
    }

}
