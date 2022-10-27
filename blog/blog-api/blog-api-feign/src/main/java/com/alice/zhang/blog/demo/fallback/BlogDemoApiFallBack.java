package com.alice.zhang.blog.demo.fallback;

import com.alice.zhang.blog.demo.BlogDemoApi;
import com.alice.zhang.support.common.dto.ResponseInfo;
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
            public ResponseInfo<String> DemoTest() {
                return new ResponseInfo(400, throwable.getMessage());
            }
        };
    }

}
