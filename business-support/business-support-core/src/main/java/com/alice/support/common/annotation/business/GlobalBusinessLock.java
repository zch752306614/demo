package com.alice.support.common.annotation.business;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 分布式锁
 * @DateTime 2024/2/29 14:01
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalBusinessLock {

    /**
     * key有传值直接用该字符串作为key
     */
    String key() default "";

    /**
     * key没传值取入参中该字段的值作为key
     * 多个字段用冒号（:）隔开
     */
    String lockField() default "";

    String paramName();

    /**
     * 默认过期时间0（自动续期），大于0不会自动续期，根据需求执行赋值
     * 自动需求的话会消耗性能，并发高的情况下有OOM的风险
     */
    long leaseTime() default 0;

    /**
     * 锁被占用时，可等待的时间，超时后抛出异常
     * 为-1时为无限等待
     */
    long waitTime() default 0;

    String message() default "系统正常处理，请稍后提交";

}

