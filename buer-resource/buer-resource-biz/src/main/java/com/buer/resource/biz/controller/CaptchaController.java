package com.buer.resource.biz.controller;

import com.buer.common.core.entity.R;
import com.buer.resource.api.vo.CaptchaVO;
import com.buer.resource.biz.service.CaptchaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生成验证码Controller
 *
 * @author zoulan
 * @since 2024-09-10
 */
@RestController
@RequestMapping("captcha")
@RequiredArgsConstructor
@Tag(name = "生成验证码Controller")
public class CaptchaController {

    private final CaptchaService captchaService;

    /**
     * 生成验证码
     *
     * @return 验证码对象
     */
    @Operation(summary = "生成验证码")
    @PostMapping()
    public R<CaptchaVO> createCaptcha() {
        return R.ok(captchaService.createCaptcha());
    }

}
