package com.alice.support.common.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description 注意，不要扫描jpa接口，否则使用jpa时会出现问题
 * @ClassName MybatisPlusConfig
 * @DateTime 2022/6/30 17:48
 * @Author ZhangChenhuang
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.alice.**.entity", "com.alice.**.dao"})
public class MybatisPlusConfig {

    @Bean
    public MySqlInjector mySqlInjector() {
        return new MySqlInjector();
    }

    /**
     * 全局配置 -逻辑删除，自动填充
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.INPUT);
        dbConfig.setLogicDeleteValue("0");
        dbConfig.setLogicNotDeleteValue("1");
        globalConfig.setSqlInjector(mySqlInjector());
        globalConfig.setDbConfig(dbConfig);
        return globalConfig;
    }

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }

    @Bean
    public InsertBatchSqlInjector easySqlInjector() {
        return new InsertBatchSqlInjector();
    }

}
