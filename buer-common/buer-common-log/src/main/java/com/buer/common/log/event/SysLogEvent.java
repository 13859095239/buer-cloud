package com.buer.common.log.event;

import com.buer.system.api.entity.SysLog;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.context.ApplicationEvent;

/**
 * 系统日志事件
 *
 * @author zoulan
 * @since 2023-06-17
 */
@Schema(description = "系统日志事件")
public class SysLogEvent extends ApplicationEvent {

    public SysLogEvent(SysLog source) {
        super(source);
    }

}
