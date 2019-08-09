package com.cloud.config;

import org.springframework.context.annotation.Bean;

public class PerplayerAutoConfiguration {

    @Bean
    public ApplicationContextUtil applicationContextUtil(){
        return new ApplicationContextUtil();
    }
}
