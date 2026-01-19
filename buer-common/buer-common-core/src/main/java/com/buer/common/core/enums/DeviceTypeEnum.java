package com.buer.common.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 设备类型
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
