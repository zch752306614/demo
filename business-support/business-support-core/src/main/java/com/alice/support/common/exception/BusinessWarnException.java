package com.alice.support.common.exception;

/**
 * @Description 业务警告
 * @Author ZhangChenhuang
 * @DateTime 2022/9/21 0021 16:40
 */
public class BusinessWarnException extends RuntimeException {

    public BusinessWarnException(final String message) {
        super(message);
    }

}
