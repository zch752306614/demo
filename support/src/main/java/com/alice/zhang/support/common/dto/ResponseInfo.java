package com.alice.zhang.support.common.dto;

import cn.hutool.core.util.ObjectUtil;
import com.alice.zhang.support.common.enums.ResponseCodeEnums;
import com.alice.zhang.support.common.exception.dto.ExceptionDTO;
import lombok.Data;

/**
 * @Description 统一返回结果
 * @Author ZhangChenhuang
 * @DateTime 2022/9/21 0021 16:15
 */
@Data
public class ResponseInfo<T> {

    /**
     * 状态码
     */
    protected int code;

    /**
     * 响应信息
     */
    protected String message;

    /**
     * 异常头部信息
     */
    private String errHead;

    /**
     * 异常类型
     */
    private String errType;

    /**
     * 返回代码
     */
    private String errCode;

    /**
     * 返回数据
     */
    private T data;

    public ResponseInfo() {
        this.code = ResponseCodeEnums.SUCCESS.code;
        this.message = ResponseCodeEnums.SUCCESS.message;
    }

    public ResponseInfo(ResponseCodeEnums statusEnums) {
        this.code = statusEnums.code;
        this.message = statusEnums.message;
    }

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     */
    public ResponseInfo(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    /**
     * 有数据返回时，状态码为200，默认提示信息为“操作成功！”
     */
    public ResponseInfo(T data) {
        this.data = data;
        this.code = ResponseCodeEnums.SUCCESS.code;
        this.message = ResponseCodeEnums.SUCCESS.message;
    }

    /**
     * 有数据返回，状态码为 200，人为指定提示信息
     */
    public ResponseInfo(T data, String msg) {
        this.data = data;
        this.code = ResponseCodeEnums.SUCCESS.code;
        this.message = msg;
    }

    public static <T> ResponseInfo<T> success() {
        return new ResponseInfo<>();
    }

    public static <T> ResponseInfo<T> success(T data) {
        return new ResponseInfo<>(data);
    }

    public static <T> ResponseInfo<T> fail(String message) {
        return new ResponseInfo<>(ResponseCodeEnums.FAIL.code, message);
    }

    public static <T> ResponseInfo<T> warm(String message) {
        return new ResponseInfo<>(ResponseCodeEnums.SUCCESS.code, message);
    }

    public void setErrorInfo(ExceptionDTO exceptionDTO) {
        this.code = ResponseCodeEnums.FAIL.code;
        if (ObjectUtil.isNotEmpty(exceptionDTO)) {
            this.errHead = exceptionDTO.getErrHead();
            this.errType = exceptionDTO.getErrType();
            this.errCode = exceptionDTO.getErrCode();
            this.message = exceptionDTO.getMessage();
        }
    }

}
