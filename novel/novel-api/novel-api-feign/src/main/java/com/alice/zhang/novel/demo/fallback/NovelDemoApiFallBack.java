package com.alice.zhang.novel.demo.fallback;

import com.alice.zhang.common.dto.ResponseInfo;
import com.alice.zhang.novel.demo.NovelDemoApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @DateTime 2022/10/27 0027$ 11:00$
 */
@Component
public class NovelDemoApiFallBack implements FallbackFactory<NovelDemoApi> {

    @Override
    public NovelDemoApi create(Throwable throwable) {
        return new NovelDemoApi() {
            @Override
            public ResponseInfo<String> DemoTest() {
                return new ResponseInfo(400, throwable.getMessage());
            }
        };
    }

}
