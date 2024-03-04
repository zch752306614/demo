package com.alice.support.common.framework.aspect;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alice.support.common.annotation.business.GlobalBusinessLock;
import com.alice.support.common.redis.service.RedissonService;
import com.alice.support.common.util.BusinessExceptionUtil;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description 分布式锁
 * @DateTime 2024/2/29 14:04
 */
@Log4j2
@Aspect
@Component
@Order(1)
public class GlobalBusinessLockAspect {

    @Resource
    private RedissonService redissonService;

    public GlobalBusinessLockAspect() {
    }

    @Around(value = "@annotation(globalBusinessLock)", argNames = "joinPoint, globalBusinessLock")
    public Object beforeMethod(ProceedingJoinPoint joinPoint, GlobalBusinessLock globalBusinessLock) throws Throwable {
        String paramName = globalBusinessLock.paramName();
        // 获取redisKey
        String redisKey = this.getRedisKey(joinPoint, globalBusinessLock, paramName);
        long leaseTime = globalBusinessLock.leaseTime();
        long waitTime = globalBusinessLock.waitTime();
        String message = globalBusinessLock.message();

        Object result;
        RLock rLock = null;
        try {
            log.info("尝试获取分布式锁，redisKey=" + redisKey);
            rLock = redissonService.tryLock(redisKey, leaseTime, waitTime, TimeUnit.MILLISECONDS, true);
            if (null == rLock) {
                BusinessExceptionUtil.dataEx(message);
            }
            log.info("获取分布式锁成功，redisKey=" + redisKey);
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("添加分布式锁业务方法执行异常，异常原因：", throwable);
            throw throwable;
        } finally {
            if (null != rLock && rLock.isLocked()) {
                redissonService.unLock(rLock);
                log.info("释放分布式锁成功，redisKey=" + redisKey);
            }
        }
        return result;
    }

    /**
     * 获取加锁的key
     *
     * @param joinPoint          JoinPoint
     * @param globalBusinessLock GlobalBusinessLock
     * @param paramName          指定入参名称
     * @return String
     */
    private String getRedisKey(ProceedingJoinPoint joinPoint, GlobalBusinessLock globalBusinessLock, String paramName) {
        //获取注解参数
        StringBuilder redisKey = new StringBuilder("globalBusinessLock");
        String key = globalBusinessLock.key();
        // 如果key不为空，则使用key的值作为redisKey
        if (ObjectUtil.isNotEmpty(key)) {
            return redisKey + ":" + key;
        }

        // 如果key为空，则使用pathId和用户Id以及入参中lockField的值作为key
        Object[] args = joinPoint.getArgs();
        String lockField = globalBusinessLock.lockField();
        if (ObjectUtil.isEmpty(args)) {
            return redisKey + ":" + lockField;
        }
        // 获取指定入参，不指定则获取第一个入参
        Object paramField;
        if (ObjectUtil.isNotEmpty(paramName)) {
            paramField = ReflectUtil.getField(args[0].getClass(), paramName);
        } else {
            paramField = args[0];
        }
        // 指定入参为空时直接赋值字段名
        String[] split = lockField.split(":");
        for (String fieldName : split) {
            if ("userId".equalsIgnoreCase(fieldName)) {
                redisKey.append(":").append("userId").append(":").append("1");
            } else {
                String fieldValue = ReflectUtil.getFieldValue(paramField, fieldName).toString();
                redisKey.append(":").append(fieldName).append(":").append(fieldValue);
            }
        }
        return redisKey.toString();
    }
}
