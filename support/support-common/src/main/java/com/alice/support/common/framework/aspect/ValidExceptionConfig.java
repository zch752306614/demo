package com.alice.support.common.framework.aspect;

import com.alice.support.common.dto.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * @Description Valid注解抛出异常
 * @DateTime 2023/12/4 17:56
 */
@Slf4j
@ControllerAdvice
public class ValidExceptionConfig {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseInfo<String> throwCustomException(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error(String.format("[@Valid异常捕获{%s}]", methodArgumentNotValidException.getMessage()));
        return ResponseInfo.fail(Objects.requireNonNull(methodArgumentNotValidException.getBindingResult().getFieldError()).getDefaultMessage());
    }
}