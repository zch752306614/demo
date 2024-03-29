package com.alice.blog.fallback;

import com.alice.blog.module.demo.detail.FeignTestDTO;
import com.alice.support.common.dto.ResponseInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @DateTime 2022/10/27 0027$ 11:00$
 */
@Component
public class BlogDemoApiFallBack implements FallbackFactory<BlogDemoApi> {

    @Override
    public BlogDemoApi create(Throwable throwable) {
        return new BlogDemoApi() {
            @Override
            public ResponseInfo<String> DemoTest(FeignTestDTO feignTestDTO) {
                return new ResponseInfo<>(400, throwable.getMessage());
            }
        };
    }

}
