package com.cloud.producer.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
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
    public BasicDataSource basicDataSource(){
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/test");
        source.setUsername("root");
        source.setPassword("tiger");
        source.setInitialSize(0);
        source.setMaxActive(20);
        source.setMaxIdle(20);
        source.setMinIdle(1);
        source.setMaxWait(60000);

/*        source.setDriverClassName(driverClassName);
        source.setUrl(url);
        source.setUsername(username);
        source.setPassword(password);
        source.setInitialSize(initialSize);
        source.setMaxActive(maxActive);
        source.setMaxIdle(maxIdle);
        source.setMinIdle(minIdle);
        source.setMaxWait(maxWait);*/
        return source;
    }

    @Bean
    public SqlSessionFactoryBean SqlSessionFactoryBean(DataSource dataSource) throws IOException {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourceResolver.getResources("mapper/*.xml");
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
//        factoryBean.setConfigLocation(new ClassPathResource("conf/mybatis-config.xml"));
        factoryBean.setMapperLocations(resources);
        return factoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setSqlSessionFactoryBeanName("SqlSessionFactoryBean");
        configurer.setBasePackage("com.cloud.producer.dao");
        return configurer;
    }
}
