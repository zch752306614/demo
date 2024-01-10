package com.alice.support.common.annotation;

import com.alice.support.common.validator.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 邮箱格式校验
 *
 * @author ZDA
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {

    String message() default "邮箱格式有误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}