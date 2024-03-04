package com.alice.support.common.validator;

import cn.hutool.core.util.ObjectUtil;
import com.alice.support.common.annotation.valid.Email;
import lombok.SneakyThrows;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 邮箱格式校验
 *
 * @author ZDA
 * @date 2021/11/20 13:51
 */
public class EmailValidator implements ConstraintValidator<Email, String> {

    public static final String EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z\\d-]+\\.)+[a-zA-Z]{2,6}$";

    @Override
    public void initialize(Email parameters) {
    }

    @SneakyThrows
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value || ObjectUtil.isEmpty(value)) {
            return true;
        } else {
            return Pattern.matches(EMAIL, value);
        }
    }

}
