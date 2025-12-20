package com.buer.resource.biz.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 删除过期的临时文件job
 * xxl定时任务
 *
 * @author zoulan
 * @since 2024-01-08
 */
@Component
@Slf4j
public class RemoveDueOssJob {

    @XxlJob("RemoveDueOssHandler")
    public void removeDueOssHandler() throws Exception {
        XxlJobHelper.log("XXL-JOB, Hello World.");
        for (int i = 0; i < 5; i++) {
            XxlJobHelper.log("beat at:" + i);
        }
    }
}
