package com.alice.support.common.validator;

import cn.hutool.core.util.ObjectUtil;
import com.alice.support.common.annotation.Month;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 *
 * @author ZDA
 * @date 2021/11/20 13:51
 */
public class MonthValidator implements ConstraintValidator<Month, Object> {

    public static final String MONTH = "^([1-9]\\d{3})(0[1-9]|10|11|12)";

    @Override
    public void initialize(Month constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null == value || ObjectUtil.isEmpty(value)) {
            return true;
        } else {
            return Pattern.matches(MONTH, value.toString());
        }
    }
}
