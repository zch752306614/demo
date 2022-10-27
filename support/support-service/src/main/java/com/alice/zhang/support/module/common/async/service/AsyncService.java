package com.alice.zhang.support.module.common.async.service;

import java.util.concurrent.CompletableFuture;

/**
 * @Description 异步测试
 * @Author ZhangChenhuang
 * @ClassName AsyncServiceImpl
 * @DateTime 2022/6/22 14:45
 */
public interface AsyncService {

    void doSomething(String message);

    CompletableFuture<String> doSomething1(String message);

    CompletableFuture<String> doSomething2(String message);

    CompletableFuture<String> doSomething3(String message);

}
