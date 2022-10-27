package com.alice.zhang.blog.module.demo.controller;

import com.alice.zhang.common.dto.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @DateTime 2022/10/27 0027$ 10:43$
 */
@Slf4j
@RestController
@RequestMapping(value = "/test")
public class DemoController {

    @GetMapping(value = "/demo")
    public ResponseInfo<String> demoTest() {
        return ResponseInfo.success("blog服务访问成功！");
    }

}