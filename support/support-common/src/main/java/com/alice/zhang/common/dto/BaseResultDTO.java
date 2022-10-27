package com.alice.zhang.common.dto;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @DateTime 2022/10/27 0027$ 11:00$
 */
public class BaseResultDTO implements Serializable {

    private static final long serialVersionUID = -8531161158890390313L;

    public static final int SUCCESS_STATUS = 1;
    public static final int FAIL_STATUS = 0;
    private static final int INIT_STATUS = -2147483648;

    protected int status = -2147483648;
    protected String message;

    public BaseResultDTO() {
    }

    public BaseResultDTO(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static BaseResultDTO fail(String msg) {
        return new BaseResultDTO(BaseResultDTO.FAIL_STATUS, msg);
    }

}

