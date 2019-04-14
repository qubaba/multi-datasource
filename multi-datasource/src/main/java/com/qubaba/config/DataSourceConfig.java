package com.qubaba.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    /**
     * 配置读取数据的数据源 配置文件以 spring.datasource.read 为前缀
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSource read() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 写数据源
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public DataSource write() {
        return DataSourceBuilder.create().build();
    }

    @Primary // 主要的
    @Bean
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(read());
        // 配置多数据源
        Map<Object, Object> map = new HashMap<>();
        map.put("read", read());
        map.put("write", write());

        dynamicDataSource.setTargetDataSources(map);
        return dynamicDataSource;
    }

    /**
     * 配置@Transactional注解事物
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
