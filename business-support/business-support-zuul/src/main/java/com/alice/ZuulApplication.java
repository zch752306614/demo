package com.alice;

import com.alice.support.common.util.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * @Description 启动类
 * @Author ZhangChenhuang
 * @DateTime 2022/9/23 0023 17:26
 */
@EnableAsync
@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Lazy(false)
    @Order(-2147481648)
    public SpringContextHolder springUtils() {
        return new SpringContextHolder();
    }
}