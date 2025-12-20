package com.buer.common.core.entity;

import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Controller层返回结果
 *
 * @author zoulan
 * @since 2023-05-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> implements Serializable {

    private T data;
    private int code;
    private String message;
    private String errorDetails;

    public static <T> R<T> ok() {
        return of(null, HttpStatus.HTTP_OK, "保存成功", null);
    }

    public static <T> R<T> ok(T model, String msg) {
        return of(model, HttpStatus.HTTP_OK, msg, null);
    }

    public static <T> R<T> ok(T model) {
        return of(model, HttpStatus.HTTP_OK, null, null);
    }

    public static <T> R<T> of(T data, Integer code, String msg, String errorDetails) {
        return new R<>(data, code, msg, errorDetails);
    }

    public static <T> R<T> fail(String msg) {
        return of(null, HttpStatus.HTTP_INTERNAL_ERROR, msg, null);
    }

    public static <T> R<T> fail(int code, String msg, String errorDetails) {
        return of(null, code, msg, errorDetails);
    }

    public static <T> R<T> fail(T model, String msg) {
        return of(model, HttpStatus.HTTP_INTERNAL_ERROR, msg, null);
    }

    public static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMessage(msg);
        return apiResult;
    }
}
