package com.alice.support.common.annotation.valid;

import com.alice.support.common.validator.MonthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 年月
 *
 * @author ZDA
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = MonthValidator.class)
public @interface Month {

    String message() default "年月格式有误，应为[yyyyMM]";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}