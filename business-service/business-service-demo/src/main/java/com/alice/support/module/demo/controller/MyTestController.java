package com.alice.support.module.demo.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.alice.blog.fallback.BlogDemoApi;
import com.alice.blog.module.demo.detail.FeignTestDTO;
import com.alice.blog.module.demo.detail.FeignTestDetailDTO;
import com.alice.novel.demo.NovelDemoApi;
import com.alice.support.common.annotation.business.GlobalBusinessLock;
import com.alice.support.common.dto.ResponseInfo;
import com.alice.support.common.redis.service.RedisService;
import com.alice.support.module.common.redis.service.RLockService;
import com.alice.support.module.demo.entity.User;
import com.alice.support.module.demo.service.UserServer;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    private BlogDemoApi blogDemoApi;

    @Resource
    private UserServer userServer;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RLockService rLockService;

    @Resource
    private RedisService redisService;

    @GetMapping(value = "/test")
    public ResponseInfo<String> demoTest() {
        FeignTestDTO feignTestDTO = new FeignTestDTO();
        List<FeignTestDetailDTO> feignTestDetailDTOList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            FeignTestDetailDTO feignTestDetailDTO = new FeignTestDetailDTO();
            feignTestDetailDTO.setA1("测试数据");
            feignTestDetailDTO.setA2("测试数据");
            feignTestDetailDTO.setA3("测试数据");
            feignTestDetailDTO.setA4("测试数据");
            feignTestDetailDTO.setA5("测试数据");
            feignTestDetailDTO.setA6("测试数据");
            feignTestDetailDTO.setA7("测试数据");
            feignTestDetailDTO.setA8("测试数据");
            feignTestDetailDTO.setA9("测试数据");
            feignTestDetailDTO.setA10("测试数据");
            feignTestDetailDTO.setA11("测试数据");
            feignTestDetailDTO.setA12("测试数据");
            feignTestDetailDTO.setA13("测试数据");
            feignTestDetailDTO.setA14("测试数据");
            feignTestDetailDTO.setA15("测试数据");
            feignTestDetailDTO.setA16("测试数据");
            feignTestDetailDTO.setA17("测试数据");
            feignTestDetailDTO.setA18("测试数据");
            feignTestDetailDTO.setA19("测试数据");
            feignTestDetailDTO.setA20("测试数据");
            feignTestDetailDTO.setA21("测试数据");
            feignTestDetailDTO.setA22("测试数据");
            feignTestDetailDTO.setA23("测试数据");
            feignTestDetailDTO.setA24("测试数据");
            feignTestDetailDTO.setA25("测试数据");
            feignTestDetailDTO.setA26("测试数据");
            feignTestDetailDTO.setA27("测试数据");
            feignTestDetailDTO.setA28("测试数据");
            feignTestDetailDTO.setA29("测试数据");
            feignTestDetailDTO.setA30("测试数据");
            feignTestDetailDTOList.add(feignTestDetailDTO);
        }
        feignTestDTO.setFeignTestDetailDTOList(feignTestDetailDTOList);
        long objectSize = ObjectSizeCalculator.getObjectSize(feignTestDTO);
        System.out.println(objectSize);
        ResponseInfo<String> stringResponseInfo = blogDemoApi.DemoTest(feignTestDTO);
        System.out.println(stringResponseInfo.getMessage());
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

    @GlobalBusinessLock(lockField = "aac002:userid", leaseTime = 10000, waitTime = 2000)
    @PostMapping("/testLock")
    public String testLock(@RequestBody TestDTO testDTO) {
        ThreadUtil.sleep(50000);
        System.out.println("接口testLock访问成功");
        return "success";
    }

    @PostMapping("/testDaemon")
    public String testDaemon() {
        String key = "testDaemon";
        if (redisService.hasKey(key)) {
            return "false";
        }
        redisService.putValue(key, "1");
        userServer.expireKey(key, 10);
        ThreadUtil.sleep(20000);
        System.out.println("接口testDaemon访问成功");
        redisService.remove(key);
        return "success";
    }
}
