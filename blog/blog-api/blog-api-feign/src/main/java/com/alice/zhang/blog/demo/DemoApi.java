package com.alice.zhang.blog.demo;


import com.alice.zhang.blog.demo.fallback.DemoApiFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @DateTime 2022/10/27 0027$ 11:00$
 */
@Component
@FeignClient(name = "blog", qualifier = "DemoApi", fallbackFactory = DemoApiFallBack.class)
public interface DemoApi {

    @GetMapping(value = "/demo")
    ResponseInfo<String> DemoTest();

}
