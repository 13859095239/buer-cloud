package com.buer.common.log.event;

import com.buer.system.api.entity.SysLog;
import com.buer.system.api.feign.RemoteLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步监听日志事件
 *
 * @author zoulan
 * @since 2023-06-17
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SysLogListener implements ApplicationListener<SysLogEvent> {

    private final RemoteLogService remoteLogService;

    @Async
    @EventListener
    public void saveSysLog(SysLogEvent event) {
        SysLog sysLog = (SysLog) event.getSource();
        remoteLogService.saveLog(sysLog);
    }

    @Override
    public void onApplicationEvent(SysLogEvent event) {

    }
}
