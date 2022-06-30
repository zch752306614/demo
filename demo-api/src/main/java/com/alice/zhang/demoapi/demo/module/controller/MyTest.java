package com.alice.zhang.demoapi.demo.module.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @ClassName MyTest
 * @DateTime 2022/6/30 16:39
 */
@RestController
@RequestMapping(value = "/test")
public class MyTest {

    @GetMapping(value = "/test")
    public String demoTest() {
        return "success";
    }

}
