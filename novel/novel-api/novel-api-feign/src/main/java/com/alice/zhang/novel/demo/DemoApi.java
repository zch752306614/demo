package com.alice.zhang.novel.demo;

import com.alice.zhang.common.dto.ResponseInfo;
import com.alice.zhang.novel.demo.fallback.DemoApiFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @DateTime 2022/10/27 0027$ 11:00$
 */
@Component
@FeignClient(name = "novel", qualifier = "DemoApi", fallbackFactory = DemoApiFallBack.class)
public interface DemoApi {

    @GetMapping(value = "/demo")
    ResponseInfo<String> DemoTest();

}
