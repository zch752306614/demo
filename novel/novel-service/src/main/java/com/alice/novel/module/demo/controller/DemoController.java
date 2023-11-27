package com.alice.novel.module.demo.controller;

import com.alice.support.common.dto.ResponseInfo;
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
        return ResponseInfo.success("novel服务访问成功！");
    }

}
