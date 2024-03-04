package com.alice.support.common.validator;

import cn.hutool.core.util.ObjectUtil;
import com.alice.support.common.annotation.valid.Length;
import lombok.SneakyThrows;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author ZDA
 * @date 2021/11/20 13:51
 */
public class LengthValidator implements ConstraintValidator<Length, Object> {

    private int min;
    private int max;

    @Override
    public void initialize(Length parameters) {
        this.min = parameters.min();
        this.max = parameters.max();
        this.validateParameters();
    }

    @SneakyThrows
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null == value || ObjectUtil.isEmpty(value)) {
            return true;
        } else {
            int length = value.toString().getBytes("GBK").length;
            return length >= this.min && length <= this.max;
        }
    }

    private void validateParameters() {
        if (this.min < 0) {
            throw new RuntimeException("The min parameter cannot be negative.");
        } else if (this.max < 0) {
            throw new RuntimeException("The max parameter cannot be negative.");
        } else if (this.max < this.min) {
            throw new RuntimeException("The length cannot be negative.");
        }
    }

}
