package com.alice.zhang.demoapi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 返回的错误码
 * @Author ZhangChenhuang
 * @DateTime 2022/9/21 0021 16:09
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnums {

    // 接口状态码
    SUCCESS(0, "success"),
    FAIL(1, "fail"),
    WARM(2, "warm"),

    // http状态码
    HTTP_STATUS_200(200, "ok"),
    HTTP_STATUS_400(400, "request error"),
    HTTP_STATUS_401(401, "no authentication"),
    HTTP_STATUS_403(403, "no authorities"),
    HTTP_STATUS_500(500, "server error");

    public final int code;

    public final String message;

}
