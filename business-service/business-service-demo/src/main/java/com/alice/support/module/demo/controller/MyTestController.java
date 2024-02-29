package com.alice.support.module.demo.controller;

import com.alice.novel.demo.NovelDemoApi;
import com.alice.support.common.dto.ResponseInfo;
import com.alice.support.module.common.redis.service.RLockService;
import com.alice.support.module.demo.entity.User;
import com.alice.support.module.demo.service.UserServer;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @ClassName MyTest
 * @DateTime 2022/6/30 16:39
 */
@RestController
@RequestMapping(value = "/test")
public class MyTestController {

    @Resource
    private NovelDemoApi novelDemoApi;

    @Resource
    private UserServer userServer;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RLockService rLockService;

    @GetMapping(value = "/test")
    public ResponseInfo<String> demoTest() {
        return ResponseInfo.success();
    }

    @GetMapping(value = "/getData")
    public ResponseInfo<User> getData() {
        return ResponseInfo.success(userServer.select(1));
    }

    @GetMapping("/lock-demo")
    public String lockDemo() {
        // 获取分布式锁对象
        RLock lock = redissonClient.getLock("myLock");

        try {
            // 尝试加锁，并设定锁的过期时间
            boolean isLocked = lock.tryLock();
            if (isLocked) {
                System.out.println("成功获取分布式锁，执行业务逻辑...");
                // 模拟业务逻辑执行
                Thread.sleep(5000);
                return "成功获取分布式锁，执行业务逻辑";
            } else {
                return "获取分布式锁失败";
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "获取分布式锁时发生异常";
        } finally {
            // 释放锁
            lock.unlock();
            System.out.println("释放分布式锁");
        }
    }

    @GetMapping("/testLock")
    public String testLock() {
        for (int i = 0; i < 10000; i++) {
            rLockService.getLock(i);
        }
        return "success";
    }
}
