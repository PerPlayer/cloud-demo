package com.cloud.producer.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Autowired
    private DataSourceProperties properties;

    // 数据源
    @Primary
    @Bean("dataSource")
    public DataSource dataSource(){
        RoutingDataSourceConfig dataSourceConfig = new RoutingDataSourceConfig();
        HashMap<String, String> routeTypeMap = Maps.newHashMap();
        routeTypeMap.put("master", "update, list");
        routeTypeMap.put("salver", "query, code");
        HashMap<Object, Object> targetDataSources = Maps.newHashMap();
        targetDataSources.put("master", dataSource1());
        targetDataSources.put("salver", dataSource2());
        dataSourceConfig.setRouteTypeMap(routeTypeMap);
        dataSourceConfig.setDefaultTargetDataSource(dataSource1());
        dataSourceConfig.setTargetDataSources(targetDataSources);
        return dataSourceConfig;
    }

//    @Bean("master")
//    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource1(){
        DruidDataSource source = new DruidDataSource();
        source.setDriverClassName(properties.getDriverClassName());
        source.setUrl(properties.getUrl());
        source.setUsername(properties.getUsername());
        source.setPassword(properties.getPassword());
        source.setInitialSize(0);
        source.setMaxActive(20);
        source.setMaxIdle(20);
        source.setMinIdle(1);
        source.setMaxWait(60000);
        try {
            source.setFilters("stat");
//            source.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return source;
    }

//    @Bean("salver")
    public DataSource dataSource2(){
        DruidDataSource source = new DruidDataSource();
        source.setDriverClassName(properties.getDriverClassName());
        source.setUrl(properties.getUrl());
        source.setUsername(properties.getUsername());
        source.setPassword(properties.getPassword());
        source.setInitialSize(0);
        source.setMaxActive(20);
        source.setMaxIdle(20);
        source.setMinIdle(1);
        source.setMaxWait(60000);
        try {
            source.setFilters("stat");
//            source.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return source;
    }


//    @Bean
//    @ConfigurationProperties("spring.datasource")
    public DataSource firstDataSource(){
        return DruidDataSourceBuilder.create().build();
    }
}
