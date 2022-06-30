package com.alice.zhang.demoapi.demo.async.service.impl;

import com.alice.zhang.demoapi.demo.async.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @Description 异步测试
 * @Author ZhangChenhuang
 * @ClassName AsyncServiceImpl
 * @DateTime 2022/6/22 14:45
 */
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    // 指定使用beanname为doSomethingExecutor的线程池
    @Async("doSomethingExecutor")
    @Override
    public void doSomething(String message) {
        log.info("do something, message={}", message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("do something error: ", e);
        }
    }

    @Async("doSomethingExecutor")
    @Override
    public CompletableFuture<String> doSomething1(String message) {
        log.info("do something1: {}", message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("do something1 error: ", e);
        }
        return CompletableFuture.completedFuture("do something1: " + message);
    }

    @Async("doSomethingExecutor")
    @Override
    public CompletableFuture<String> doSomething2(String message) {
        log.info("do something2: {}", message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("do something2 error: ", e);
        }
        return CompletableFuture.completedFuture("; do something2: " + message);
    }

    @Async("doSomethingExecutor")
    @Override
    public CompletableFuture<String> doSomething3(String message) {
        log.info("do something3: {}", message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("do something3 error: ", e);
        }
        return CompletableFuture.completedFuture("; do something3: " + message);
    }

}
