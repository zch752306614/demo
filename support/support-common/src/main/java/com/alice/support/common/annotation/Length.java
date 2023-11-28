package com.alice.support.common.annotation;

import com.alice.support.common.validator.LengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 字节长度
 * @author ZDA
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = LengthValidator.class)
public @interface Length {

    int min() default 0;

    int max() default 2147483647;

    String message() default "{org.hibernate.validator.constraints.Length.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}