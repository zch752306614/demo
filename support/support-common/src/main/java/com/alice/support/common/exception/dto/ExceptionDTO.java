package com.alice.support.common.exception.dto;

import lombok.Data;

/**
 * @Description 异常信息统一规范传输父类
 * @Author ZhangChenhuang
 * @DateTime 2022/9/21 0021 16:03
 */
@Data
public class ExceptionDTO {

    private String errHead;

    private String errType;

    private String errCode;

    private String message;

}
