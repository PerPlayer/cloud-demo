package com.cloud.consumer.service;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cloud-producer")
public interface FeignService {

    @RequestMapping("/hello")
    String hello(@RequestParam("name") String name);
}
