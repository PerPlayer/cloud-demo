package com.cloud.producer.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@AutoConfigureAfter(DataSourceConfig.class)
@Import(DataSourceConfig.class)
@Configuration
public class MybatisConfig {
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
}
