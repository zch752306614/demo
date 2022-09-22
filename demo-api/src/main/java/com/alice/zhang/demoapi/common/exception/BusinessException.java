package com.alice.zhang.demoapi.common.exception;

/**
 * @Description 业务异常
 * @Author ZhangChenhuang
 * @DateTime 2022/9/21 0021 15:58
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 3969831803584892226L;

    public BusinessException() {
    }

    public BusinessException(final String message) {
        super(message);
    }

    public BusinessException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    public BusinessException(final Throwable throwable) {
        super(throwable);
    }
}