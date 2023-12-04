package com.alice.support.common.annotation;

import com.alice.support.common.validator.SecondValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author ZDA
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = SecondValidator.class)
public @interface Second {

    String message() default "时间格式有误，应为[yyyyMMddHHmmss]";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}