package com.alice.support.common.exception;

import cn.hutool.core.text.CharSequenceUtil;
import com.alice.support.common.consts.ExceptionConstants;
import com.alice.support.common.dto.ResponseInfo;
import com.alice.support.common.exception.dto.ExceptionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description 统一异常处理
 * @Author ZhangChenhuang
 * @DateTime 2022/9/21 0021 16:18
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数格式异常处理
     *
     * @param ex 异常信息
     * @return ResponseInfo<String>
     */
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseInfo<String> badRequestException(IllegalArgumentException ex) {
        log.error("参数格式不合法：{}", ex.getMessage());
        return new ResponseInfo<>(HttpStatus.BAD_REQUEST.value(), ExceptionConstants.EXCEPTION_ILLEGAL_PARAM_POINTER);
    }

    /**
     * 权限不足异常处理
     *
     * @param ex 异常信息
     * @return ResponseInfo<String>
     */
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseInfo<String> badRequestException(AccessDeniedException ex) {
        return new ResponseInfo<>(HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }

    /**
     * 参数缺失异常处理
     *
     * @param ex 异常信息
     * @return ResponseInfo<String>
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseInfo<String> badRequestException(MissingServletRequestParameterException ex) {
        log.error("缺少必填参数，{}", ex.getMessage());
        return new ResponseInfo<>(HttpStatus.BAD_REQUEST.value(), ExceptionConstants.EXCEPTION_MISS_PARAM_POINTER);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseInfo<String> throwCustomException(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("[ @Valid异常捕获 ] " + methodArgumentNotValidException.getMessage());
        return ResponseInfo.fail(Objects.requireNonNull(methodArgumentNotValidException.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * 空指针异常
     *
     * @param ex 异常信息
     * @return ResponseInfo<String>
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseInfo<String> handleTypeMismatchException(NullPointerException ex) {
        log.error("空指针异常，{}", ex.getMessage());
        return ResponseInfo.fail(ExceptionConstants.EXCEPTION_NULL_POINTER);
    }

    /**
     * BindException异常信息处理
     *
     * @param ex 异常信息
     * @return ResponseInfo<String>
     */
    @ExceptionHandler(BindException.class)
    public ResponseInfo<String> handleBindException(BindException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(allErrors)) {
            List<String> collect = allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            log.error(collect.toString(), ex);
            return ResponseInfo.fail(collect.toString());
        }
        String message = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        log.error(message, ex);
        return ResponseInfo.fail(message);
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseInfo<String> handleUnexpectedServer(Exception ex) {
//        log.error("系统异常：", ex);
//        return ResponseInfo.fail(ExceptionConstants.DEFAULT_ERR_MESSAGE);
//    }

    /**
     * 系统异常处理
     *
     * @param ex 异常信息
     * @return ResponseInfo<String>
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseInfo<String> exception(Exception ex) {
        ResponseInfo<String> responseInfo = new ResponseInfo<>();
        ExceptionDTO exceptionDTO;
        String msg = ex.getMessage();

        if (ex instanceof BusinessException) {
            return ResponseInfo.fail(CharSequenceUtil.isBlank(msg) ? ExceptionConstants.DEFAULT_ERR_MESSAGE : msg);
        } else if (ex instanceof BusinessWarnException) {
            return ResponseInfo.warm(CharSequenceUtil.isBlank(msg) ? ExceptionConstants.DEFAULT_WARM_MESSAGE : msg);
        } else {
            msg = ex.getMessage();
            exceptionDTO = new ExceptionDTO();
            exceptionDTO.setErrType(ExceptionConstants.BUSINESS_EX_TYPE_SYSTEM);
            exceptionDTO.setErrCode(ExceptionConstants.BUSINESS_EX_CODE_SYSTEM_DEFAULT);
            exceptionDTO.setMessage(CharSequenceUtil.isBlank(msg) ? ExceptionConstants.DEFAULT_ERR_MESSAGE : msg);
        }
        log.error(CharSequenceUtil.isBlank(msg) ? ExceptionConstants.DEFAULT_ERR_MESSAGE : msg, ex);
        responseInfo.setErrorInfo(exceptionDTO);
        return responseInfo;
    }
}
