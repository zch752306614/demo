package com.alice.support.common.annotation;

import com.alice.support.common.validator.DayValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 日期
 *
 * @author ZDA
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = DayValidator.class)
public @interface Day {

    String message() default "日期格式有误，应为[yyyyMMdd]";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}