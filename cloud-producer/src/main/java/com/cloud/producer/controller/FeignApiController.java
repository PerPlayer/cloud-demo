package com.cloud.producer.controller;


import com.cloud.feign.service.FeignApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;


@RestController
public class FeignApiController implements FeignApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DiscoveryClient client;

    @Override
    public String hello(String name) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            int sleepTime = new Random().nextInt(3000);
            logger.info("sleepTime: " + sleepTime);
            Thread.sleep(sleepTime);
            logger.info("host: " + instance.getHost());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Feign api response: " + name;
    }
}
