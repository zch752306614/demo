package com.alice.support.common.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description Redisson
 * @DateTime 2024/2/29 15:58
 */
@Slf4j
@Service
public class RedissonService {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 自动续期锁
     * 获取锁失败一会直等待,直到获取锁
     * @param key 锁的key值
     * @return RLock
     */
    public RLock lock(String key) {
        return this.lock(key, 0L, TimeUnit.SECONDS, false);
    }

    /**
     * 自动续期锁
     * 异步获取锁，主线程不会阻塞
     * 获取锁失败一会直等待,直到获取锁
     * @param key 锁的key值
     * @return RLock
     */
    public RLock lockAsync(String key) {
        return this.lockAsync(key, 0L, TimeUnit.SECONDS, false);
    }

    /**
     * 获取锁失败一会直等待,直到获取锁
     *
     * @param key 锁的key值
     * @param lockTime 锁的时间，等于0会自动续期，大于0不会自动续期
     * @param unit 单位
     * @param fair 是否公平锁
     * @return RLock
     */
    public RLock lock(String key, long lockTime, TimeUnit unit, boolean fair) {
        RLock lock = getLock(key, fair);
        if (lockTime > 0L) {
            lock.lock(lockTime, unit);
        } else {
            lock.lock();
        }
        return lock;
    }

    /**
     * 异步获取锁，主线程不会阻塞
     * 获取锁失败一会直等待,直到获取锁
     *
     * @param key 锁的key值
     * @param lockTime 锁的时间，等于0会自动续期，大于0不会自动续期
     * @param unit 单位
     * @param fair 是否公平锁
     * @return RLock
     */
    public RLock lockAsync(String key, long lockTime, TimeUnit unit, boolean fair) {
        RLock lock = getLock(key, fair);
        if (lockTime > 0L) {
            lock.lockAsync(lockTime, unit);
        } else {
            lock.lockAsync();
        }
        return lock;
    }

    /**
     * 自动续期锁
     * 尝试获取锁，获取不到超时异常
     * @param key 锁的key值
     * @param tryTime 尝试获取锁的时间
     * @return RLock
     */
    public RLock tryLock(String key, long tryTime) throws Exception {
        return this.tryLock(key, tryTime, 0L, TimeUnit.SECONDS, false);
    }

    /**
     * 尝试获取锁，获取不到超时异常
     *
     * @param key 锁的key值
     * @param leaseTime 锁的时间，等于0会自动续期，大于0不会自动续期
     * @param waitTime 尝试获取锁的时间
     * @param unit 单位
     * @param fair 是否公平锁
     * @return RLock
     */
    public RLock tryLock(String key, long leaseTime, long waitTime, TimeUnit unit, boolean fair) throws Exception {
        RLock lock = getLock(key, fair);
        // leaseTime>0不支持自动续期，leaseTime<0具有Watch Dog 自动延期机制 默认续30s 每隔30/3=10 秒续到30s
        boolean lockAcquired = leaseTime > 0L ? lock.tryLock(waitTime, leaseTime, unit) : lock.tryLock(waitTime, unit);
        if (lockAcquired) {
            return lock;
        }
        return null;
    }

    /**
     * 自动续期锁
     * 异步获取锁，主线程不会阻塞
     * 尝试获取锁，获取不到超时异常
     * @param key 锁的key值
     * @param tryTime 尝试获取锁的时间
     * @return RLock
     */
    public RLock tryLockAsync(String key, long tryTime) throws Exception {
        return this.tryLockAsync(key, tryTime, 0L, TimeUnit.SECONDS, false);
    }

    /**
     * 尝试获取锁，获取不到超时异常
     *
     * @param key 锁的key值
     * @param tryTime 尝试获取锁的时间
     * @param lockTime 锁的时间，等于0会自动续期，大于0不会自动续期
     * @param unit 单位
     * @param fair 是否公平锁
     * @return RLock
     */
    public RLock tryLockAsync(String key, long tryTime, long lockTime, TimeUnit unit, boolean fair) throws Exception {
        RLock lock = getLock(key, fair);
        // lockTime>0不支持自动续期，lockTime<0具有Watch Dog 自动延期机制 默认续30s 每隔30/3=10 秒续到30s
        RFuture<Boolean> lockAcquired = lockTime > 0L ? lock.tryLockAsync(tryTime, lockTime, unit) : lock.tryLockAsync(tryTime, unit);
        if (lockAcquired.isSuccess()) {
            return lock;
        }
        return null;
    }

    /**
     * 获取锁
     *
     * @param lockKey 锁key
     * @param fair    是否获取公平锁
     * @return RLock
     */
    private RLock getLock(String lockKey, boolean fair) {
        return fair ? redissonClient.getFairLock(lockKey) : redissonClient.getLock(lockKey);
    }

    private boolean lockLoad(RLock rLock) {
        return rLock.isLocked();
    }

    /**
     * 释放锁
     *
     * @param rLock RLock
     */
    public void unLock(RLock rLock) {
        if (rLock.isLocked()) {
            try {
                rLock.unlock();
            } catch (IllegalMonitorStateException e) {
                log.error("释放分布式锁异常", e);
            }
        }
    }

    /**
     * 释放锁
     *
     * @param rLock RLock
     */
    public void unLockAsync(RLock rLock) {
        if (rLock.isLockedAsync().isSuccess()) {
            try {
                rLock.unlockAsync();
            } catch (IllegalMonitorStateException e) {
                log.error("释放分布式锁异常", e);
            }
        }
    }

}
