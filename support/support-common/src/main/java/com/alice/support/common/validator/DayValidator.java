package com.alice.support.common.validator;

import cn.hutool.core.util.ObjectUtil;
import com.alice.support.common.annotation.Day;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 *
 * @author ZDA
 * @date 2021/11/20 13:51
 */
public class DayValidator implements ConstraintValidator<Day, Object> {

    public static final String DATA = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|" +
            "((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|" +
            "((0[48]|[2468][048]|[3579][26])00))0229)$";

    @Override
    public void initialize(Day constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null == value || ObjectUtil.isEmpty(value)) {
            return true;
        } else {
            return Pattern.matches(DATA, value.toString());
        }
    }
}
