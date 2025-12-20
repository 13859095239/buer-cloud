package com.buer.resource.biz.controller;

import cn.hutool.core.util.IdUtil;
import com.buer.common.core.entity.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生成 Controller
 *
 * @author zoulan
 * @since 2024-01-27
 */
@RestController
@RequestMapping("generate")
@RequiredArgsConstructor
@Tag(name = "生成")
public class GenerateController {

    /**
     * 后端生成唯一id
     *
     * @return 雪花id
     */
    @Operation(summary = "生成id")
    @GetMapping(value = "/id")
    public R<Long> generateId() {
        long id = IdUtil.getSnowflakeNextId();
        return R.ok(id);
    }

}
