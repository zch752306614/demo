package com.alice.zhang.support.common.dto;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @DateTime 2022/10/27 0027$ 11:00$
 */
public class BusiResultDTO extends BaseResultDTO {

    private static final long serialVersionUID = 3171788164804006153L;

    public BusiResultDTO() {
    }

    public BusiResultDTO(int status, String message) {
        super(status, message);
    }

}

