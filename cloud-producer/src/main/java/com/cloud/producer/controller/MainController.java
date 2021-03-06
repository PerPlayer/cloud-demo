package com.cloud.producer.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DiscoveryClient client;

    @RequestMapping("/hello")
    public String index(@RequestParam String name){
        ServiceInstance instance = client.getInstances(client.getServices().get(0)).get(0);

        logger.info("host: {}, ip: {}", instance.getHost(), instance.getServiceId());
        return "Feign response: " + name;
    }
}
