package com.alice.blog.fallback;

import com.alice.blog.module.demo.detail.FeignTestDTO;
import com.alice.support.common.dto.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @DateTime 2022/10/27 0027$ 11:00$
 */
@Component
@FeignClient(name = "blog-service-dev", qualifier = "DemoApi", fallbackFactory = BlogDemoApiFallBack.class)
public interface BlogDemoApi {

    @PostMapping(value = "/test/demo")
    ResponseInfo<String> DemoTest(FeignTestDTO feignTestDTO);

}
