package com.buer.flow.biz.utils;

import com.buer.flow.api.constant.ListenerEventEnum;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListenerUtil {
    //    private final RestTemplate restTemplate;
    //    private final RuntimeService runtimeService;
    //    private final ActReModelService actReModelService;

    public void postFlowListener(ListenerEventEnum event, String procInstId, String value, Map<String, Object> variables) {
        //        var processInstance = runtimeService.createProcessInstanceQuery()
        //                .processInstanceId(procInstId)
        //                .singleResult();
        //        ActReModel actReModel = actReModelService.getModelByKey(processInstance.getProcessDefinitionKey());
        //        String apiUrl = actReModel.getApiPath() + "/flowListener";
        //        ListenerDTO listenerDTO = new ListenerDTO()
        //                .setEvent(event)
        //                .setProcInstId(procInstId)
        //                .setVariables(variables)
        //                .setValue(value);
        //        R result = restTemplate.postForObject(apiUrl, listenerDTO
        //                , R.class);
    }
}
