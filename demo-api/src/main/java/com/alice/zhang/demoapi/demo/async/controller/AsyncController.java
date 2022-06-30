package com.alice.zhang.demoapi.demo.async.controller;

import com.alice.zhang.demoapi.demo.async.service.AsyncService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * @Description 异步测试
 * @Author ZhangChenhuang
 * @ClassName AsyncController
 * @DateTime 2022/6/22 14:44
 */
@RestController
public class AsyncController {

    @Resource
    private AsyncService asyncService;

    @GetMapping("/open/something")
    public String something() {
        int count = 10;
        for (int i = 0; i < count; i++) {
            asyncService.doSomething("index = " + i);
        }
        return "success";
    }

    @SneakyThrows
    @GetMapping("/open/somethings")
    public String somethings() {
        CompletableFuture<String> createOrder = asyncService.doSomething1("create order");
        CompletableFuture<String> reduceAccount = asyncService.doSomething2("reduce account");
        CompletableFuture<String> saveLog = asyncService.doSomething3("save log");

        // 等待所有任务都执行完
        CompletableFuture.allOf(createOrder, reduceAccount, saveLog).join();
        // 获取每个任务的返回结果
        return createOrder.get() + reduceAccount.get() + saveLog.get();
    }

}

