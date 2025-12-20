package com.buer.system.biz.runner;

import com.buer.system.biz.service.SysDictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化system模块对应业务数据
 *
 * @author zoulan
 * @since 2025-09-09
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SystemApplicationRunner implements ApplicationRunner {

    private final SysDictService sysDictService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        sysDictService.resetDictCache();
        //log.info("完成字典缓存");
    }

}
