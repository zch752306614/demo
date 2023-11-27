package com.alice.blog.demo;

import com.alice.blog.demo.fallback.BlogDemoApiFallBack;
import com.alice.support.common.dto.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @DateTime 2022/10/27 0027$ 11:00$
 */
@Component
@FeignClient(name = "blog-service", qualifier = "DemoApi", fallbackFactory = BlogDemoApiFallBack.class)
public interface BlogDemoApi {

    @GetMapping(value = "/test/demo")
    ResponseInfo<String> DemoTest();

}
