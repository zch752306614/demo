package com.alice.zhang.support.demo.module.controller;

import com.alice.zhang.support.demo.module.entity.User;
import com.alice.zhang.support.demo.module.service.UserServer;
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
    private UserServer userServer;

    @GetMapping(value = "/test")
    public String demoTest() {
        return "success";
    }

    @GetMapping(value = "/getData")
    public User getData() {
        return userServer.select(1);
    }

}
