package com.cloud.zuul;

import com.cloud.zuul.filter.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableZuulProxy
@SpringCloudApplication
@ServletComponentScan
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

//    @Bean
    public AccessFilter accessFilter(){
        return new AccessFilter();
    }
}
