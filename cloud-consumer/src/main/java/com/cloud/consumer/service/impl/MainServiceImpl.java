package com.cloud.consumer.service.impl;

import com.cloud.consumer.service.MainService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by SHW on 2019/6/1.
 */
@Service
public class MainServiceImpl implements MainService{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate template;

    @HystrixCommand(fallbackMethod = "callback")
    @Override
    public String hello() {
        String body = template.getForEntity("http://cloudproducer/hello?name=xm", String.class).getBody();
        return "Hi, Consumer: " + body;
    }

    private String callback(){
        return "callback...";
    }
}
