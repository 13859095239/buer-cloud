package com.buer.flow.biz.Listener;

import com.buer.flow.api.constant.ListenerEventEnum;
import com.buer.flow.biz.utils.ListenerUtil;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicExecutionListener implements ExecutionListener {
    private final ListenerUtil listenerUtil;
    @Setter
    private Expression msg1;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        String eventName = delegateExecution.getEventName();
        String procInstId = delegateExecution.getProcessInstanceId();
        Map<String, Object> variables = delegateExecution.getVariables();
        ListenerEventEnum event = null;
        switch (eventName) {
            case "start" -> event = ListenerEventEnum.START_EXECUTION;
            case "end" -> event = ListenerEventEnum.END_EXECUTION;
            case "take" -> event = ListenerEventEnum.TAKE_EXECUTION;
        }
        listenerUtil.postFlowListener(event, procInstId, msg1.getExpressionText(), variables);
    }
}
