package com.alice.zhang.support.module.demo.controller;

import com.alice.zhang.blog.demo.BlogDemoApi;
import com.alice.zhang.novel.demo.NovelDemoApi;
import com.alice.zhang.support.common.dto.ResponseInfo;
import com.alice.zhang.support.module.demo.entity.User;
import com.alice.zhang.support.module.demo.service.UserServer;
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
    BlogDemoApi blogDemoApi;
    @Resource
    NovelDemoApi novelDemoApi;

    @Resource
    private UserServer userServer;

    @GetMapping(value = "/test")
    public ResponseInfo<String> demoTest() {
        return ResponseInfo.success();
    }

    @GetMapping(value = "/getData")
    public ResponseInfo<User> getData() {
        return ResponseInfo.success(userServer.select(1));
    }

}
