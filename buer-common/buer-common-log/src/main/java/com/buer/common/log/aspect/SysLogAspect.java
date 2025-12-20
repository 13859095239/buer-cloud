package com.buer.common.log.aspect;

import com.buer.common.log.enums.LogTypeEnum;
import com.buer.common.log.event.SysLogEvent;
import com.buer.common.log.util.SysLogUtils;
import com.buer.system.api.entity.SysLog;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * 操作日志使用 Aspect
 *
 * @author zoulan
 * @since 2023-06-17
 */
@Slf4j
@Aspect
@AutoConfiguration
public class SysLogAspect implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Around("@annotation(sysLogAnnotation)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, com.buer.common.log.annotation.SysLog sysLogAnnotation) {
        String strClassName = point.getTarget()
                .getClass()
                .getName();
        String strMethodName = point.getSignature()
                .getName();
        log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);
        SysLog log = SysLogUtils.getSysLog(point, sysLogAnnotation);
        // 发送异步日志事件，最终算出方法执行时间
        Long startTime = System.currentTimeMillis();
        Object obj;
        try {
            obj = point.proceed();
        } catch (Exception e) {
            log.setType(LogTypeEnum.ERROR.getType());
            log.setException(e.getMessage());
            throw e;
        } finally {
            Long endTime = System.currentTimeMillis();
            log.setTime(endTime - startTime);
            publisher.publishEvent(new SysLogEvent(log));
        }
        return obj;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
