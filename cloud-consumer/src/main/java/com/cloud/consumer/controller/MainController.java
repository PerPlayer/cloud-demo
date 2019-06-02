package com.cloud.consumer.controller;


import com.cloud.consumer.service.MainService;
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
    private MainService mainService;

    @RequestMapping("/hello")
    public String index(){
        return mainService.hello();
    }
}
