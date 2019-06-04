package com.cloud.consumer.controller;


import com.cloud.consumer.service.FeignApiService;
import com.cloud.consumer.service.FeignService;
import com.cloud.consumer.service.MainService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/")
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FeignService feignService;

    @Autowired
    private FeignApiService feignApiService;

    @Autowired
    private MainService mainService;

    @RequestMapping("/hello")
    public String index(){
        return mainService.hello();
    }
/*
    @RequestMapping("/feign/hello")
    public String feignHello(){
        return "Hi, Feign Consumer: " + feignService.hello("xm");
    }*/


    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping("/feign/hello")
    public String feignApiHello(){
        return "Hi, Feign Api Consumer: " + feignApiService.hello("xx");
    }

    private String fallback(){
        return "It's Failed...";
    }
}
