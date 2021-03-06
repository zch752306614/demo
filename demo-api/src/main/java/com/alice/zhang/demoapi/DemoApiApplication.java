package com.alice.zhang.demoapi;

import com.alice.zhang.demoapi.common.util.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.alice.zhang"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM)})
public class DemoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApiApplication.class, args);
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
