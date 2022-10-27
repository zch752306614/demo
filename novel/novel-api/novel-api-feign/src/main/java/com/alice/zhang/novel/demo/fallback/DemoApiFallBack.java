package com.alice.zhang.novel.demo.fallback;

import com.alice.zhang.common.dto.BusiResultDTO;
import com.alice.zhang.common.dto.ResponseInfo;
import com.alice.zhang.novel.demo.DemoApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @DateTime 2022/10/27 0027$ 11:00$
 */
@Component
public class DemoApiFallBack implements FallbackFactory<DemoApi> {

    @Override
    public DemoApi create(Throwable throwable) {
        return new DemoApi() {
            @Override
            public ResponseInfo<String> DemoTest() {
                return new ResponseInfo(400, throwable.getMessage());
            }
        };
    }

}
