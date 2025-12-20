package com.buer.flow.biz.Listener;

import com.buer.flow.api.constant.ListenerEventEnum;
import com.buer.flow.biz.utils.ListenerUtil;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class BasicTaskListener implements TaskListener {
    private final ListenerUtil listenerUtil;
    @Setter
    private Expression msg1;

    @Override
    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        String procInstId = delegateTask.getProcessInstanceId();
        Map<String, Object> variables = delegateTask.getVariables();
        ListenerEventEnum event = null;
        switch (eventName) {
            case "creat" -> event = ListenerEventEnum.CREATE_TASK;
            case "assignment" -> event = ListenerEventEnum.ASSIGNMENT_TASK;
            case "complete" -> event = ListenerEventEnum.COMPLETE_TASK;
            case "delete" -> event = ListenerEventEnum.DELETE_TASK;
        }
        listenerUtil.postFlowListener(event, procInstId, msg1.getExpressionText(), variables);
    }
}
