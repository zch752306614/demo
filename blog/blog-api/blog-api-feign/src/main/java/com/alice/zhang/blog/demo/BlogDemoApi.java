package com.alice.zhang.blog.demo;

import com.alice.zhang.blog.demo.fallback.BlogDemoApiFallBack;
import com.alice.zhang.common.dto.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @DateTime 2022/10/27 0027$ 11:00$
 */
@Component
@FeignClient(name = "blog", qualifier = "DemoApi", fallbackFactory = BlogDemoApiFallBack.class)
public interface BlogDemoApi {

    @GetMapping(value = "/demo")
    ResponseInfo<String> DemoTest();

}
