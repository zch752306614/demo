package com.alice.support.common.nacos.config;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.cloud.nacos.discovery.NacosWatch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.CommonsClientAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @Description nacos客户端注册至服务端时，更改服务详情中的元数据，元数据显示服务注册时间
 * @Author ZhangChenhuang
 * @DateTime 2022/9/21 0021 15:14
 */
@Configuration
@ConditionalOnNacosDiscoveryEnabled
@AutoConfigureBefore({SimpleDiscoveryClientAutoConfiguration.class, CommonsClientAutoConfiguration.class})
public class NacosDiscoveryClientAutoConfig {

    @Value("${spring.profiles.active}")
    public String profile;

    public NacosDiscoveryClientAutoConfig() {
    }

    @Bean
    @ConditionalOnMissingBean
    public NacosDiscoveryProperties nacosProperties() {
        return new NacosDiscoveryProperties();
    }

    @Bean
    public DiscoveryClient nacosDiscoveryClient(NacosServiceDiscovery nacosServiceDiscovery) {
        return new NacosDiscoveryClient(nacosServiceDiscovery);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = {"spring.cloud.nacos.discovery.watch.enabled"}, matchIfMissing = true)
    public NacosWatch nacosWatch(NacosDiscoveryProperties nacosDiscoveryProperties) {
        //原来的元数据全部清空
        nacosDiscoveryProperties.setMetadata(new HashMap<>(10));
        //更改服务详情中的元数据，增加服务注册时间
        nacosDiscoveryProperties.getMetadata().put("startup.time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        nacosDiscoveryProperties.getMetadata().put("spring.profiles.active", profile);
        return new NacosWatch(nacosDiscoveryProperties);
    }

}
