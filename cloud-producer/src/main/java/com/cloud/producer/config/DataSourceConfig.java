package com.cloud.producer.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.github.pagehelper.PageInterceptor;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class DataSourceConfig {

    // 数据源
    @Bean
//    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(DataSourceProperties properties){
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(properties.getDriverClassName());
        source.setUrl(properties.getUrl());
        source.setUsername(properties.getUsername());
        source.setPassword(properties.getPassword());
        source.setInitialSize(0);
        source.setMaxActive(20);
        source.setMaxIdle(20);
        source.setMinIdle(1);
        source.setMaxWait(60000);
        return source;
    }


//    @Bean
//    @ConfigurationProperties("spring.datasource")
    public DataSource firstDataSource(){
        return DruidDataSourceBuilder.create().build();
    }
}
