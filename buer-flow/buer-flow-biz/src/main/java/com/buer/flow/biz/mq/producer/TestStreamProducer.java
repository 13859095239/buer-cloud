package com.buer.flow.biz.mq.producer;

import com.buer.flow.api.entity.ActExtCategory;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class TestStreamProducer {

    public static final String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
    @Autowired
    private StreamBridge streamBridge;

    public void streamTestMsg(String name) {
        // 构建消息对象
        ActExtCategory testMessaging = ActExtCategory.create()
            .setName(name)
            .setName(UUID.randomUUID()
                .toString())
            .setId(1111L);
        for (int i = 0; i < 100; i++) {
            String key = "KEY" + i;
            Map<String, Object> headers = new HashMap<>();
            headers.put(MessageConst.PROPERTY_KEYS, key);
            headers.put(MessageConst.PROPERTY_ORIGIN_MESSAGE_ID, i);
            Message<String> msg = new GenericMessage(i, headers);
            streamBridge.send("demo-out-0", msg);
        }
    }
}
