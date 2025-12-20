package com.buer.common.core.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 设备类型
 *
 * @author zoulan
 * @since 2023-06-08
 */
@Getter
@RequiredArgsConstructor
public enum DeviceTypeEnum {

    /**
     * pc端
     */
    PC("pc"),

    /**
     * app端
     */
    APP("app"),

    /**
     * 小程序端
     */
    XCX("xcx");

    private final String device;
}