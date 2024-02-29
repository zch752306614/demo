package com.alice.support.module.common.redis.service.impl;

import com.alice.support.module.common.redis.service.RLockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @DateTime 2024/2/23 17:58
 */
@Service
public class RLockServiceImpl implements RLockService {

    @Resource
    private RedissonClient redissonClient;

    @Async
    @Override
    public void getLock(int num) {
        RLock rLock = redissonClient.getLock("myLock");
        try {
            boolean lockFlag = rLock.tryLock();
            if (lockFlag) {
                System.out.println("事务" + num + "成功获取分布式锁，执行业务逻辑...");
                // 模拟业务逻辑执行
                Thread.sleep(200);
                System.out.println("事务" + num + "成功获取分布式锁，完成业务逻辑");
            } else {
                System.out.println("事务" + num + "获取分布式锁失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("事务" + num + "获取分布式锁时发生异常");
        } finally {
            // 释放锁
            rLock.unlock();
            System.out.println("事务" + num + "释放分布式锁");
        }
    }

}
