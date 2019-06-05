package com.cloud.zuul;

import com.cloud.zuul.filter.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;

@EnableWebMvc
@EnableZuulProxy
@SpringCloudApplication
public class ZuulApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ZuulApplication.class, args);
        new SpringApplicationBuilder(ZuulApplication.class).web(true).run();
    }

    @Bean
    public AccessFilter accessFilter(){
        return new AccessFilter();
    }
}
