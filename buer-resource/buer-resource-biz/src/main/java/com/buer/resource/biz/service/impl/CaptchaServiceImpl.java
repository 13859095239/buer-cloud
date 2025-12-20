package com.buer.resource.biz.service.impl;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.IdUtil;
import com.buer.common.core.constant.CacheConstants;
import com.buer.common.core.exception.CaptchaException;
import com.buer.common.redis.util.RedisUtils;
import com.buer.resource.api.vo.CaptchaVO;
import com.buer.resource.biz.config.properties.CaptchaProperties;
import com.buer.resource.biz.service.CaptchaService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * 验证码处理 ServiceImpl
 *
 * @author zoulan
 * @since 2024-09-10
 */
@Service
@AllArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

    // 验证码配置
    private final CaptchaProperties captchaProperties;
    // 随机码生成字符串
    private final RandomGenerator charRandomGenerator = new RandomGenerator("23456789abcdefghjklmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ", 4);
    // 随机码生成数字
    private final RandomGenerator mathRandomGenerator = new RandomGenerator("1234567890", 4);

    /**
     * 生成验证码
     *
     * @return 验证码信息
     */
    @Override
    public CaptchaVO createCaptcha() throws CaptchaException {
        // 获取验证码id
        String captchaId = IdUtil.getSnowflakeNextIdStr();
        // 验证码在缓存中的key
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + captchaId;
        AbstractCaptcha abstractCaptcha = getAbstractCaptcha();
        // 获取验证码值
        String captchaValue = abstractCaptcha.getCode();
        // 设置缓存
        RedisUtils.setCacheObject(verifyKey, captchaValue, Duration.ofSeconds(CacheConstants.CAPTCHA_EXPIRATION));
        return new CaptchaVO()
            .setCaptchaId(captchaId)
            .setCaptchaImage(abstractCaptcha.getImageBase64Data());
    }

    /**
     * 获取验证码生成对象
     */
    @NotNull
    private AbstractCaptcha getAbstractCaptcha() {
        RandomGenerator randomGenerator = switch (captchaProperties.getType()) {
            case "char" -> charRandomGenerator;
            case "math" -> mathRandomGenerator;
            default -> null;
        };
        // 定义图形验证码的长、宽、验证码字符数、干扰元素个数
        AbstractCaptcha abstractCaptcha = switch (captchaProperties.getCategory()) {
            // 圆圈干扰
            case "circle" -> CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
            // 扭曲干扰
            case "shear" -> CaptchaUtil.createShearCaptcha(200, 100, 4, 20);
            // GIF
            case "gif" -> CaptchaUtil.createGifCaptcha(200, 100, 4, 20, 0);
            // line 线段干扰
            default -> CaptchaUtil.createLineCaptcha(200, 100, 4, 20);
        };
        // 设置自动生成规则
        abstractCaptcha.setGenerator(randomGenerator);
        // 创建验证码
        abstractCaptcha.createCode();
        return abstractCaptcha;
    }
}
