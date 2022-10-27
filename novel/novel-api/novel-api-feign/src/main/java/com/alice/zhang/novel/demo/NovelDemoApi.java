package com.alice.zhang.novel.demo;

import com.alice.zhang.support.common.dto.ResponseInfo;
import com.alice.zhang.novel.demo.fallback.NovelDemoApiFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @DateTime 2022/10/27 0027$ 11:00$
 */
@Component
@FeignClient(name = "novel-service", qualifier = "DemoApi", fallbackFactory = NovelDemoApiFallBack.class)
public interface NovelDemoApi {

    @GetMapping(value = "/test/demo")
    ResponseInfo<String> DemoTest();

}
