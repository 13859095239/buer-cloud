package com.buer.resource.biz.runner;

import com.buer.resource.biz.service.SysOssConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化resource模块对应业务数据
 *
 * @author zoulan
 * @since 2023-08-27
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ResourceApplicationRunner implements ApplicationRunner {

    private final SysOssConfigService ossConfigService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ossConfigService.init();
        log.info("初始化OSS配置成功");
    }

}
