package com.cloud.consumer.controller;


import com.cloud.consumer.service.FeignApiService;
import com.cloud.consumer.service.FeignService;
import com.cloud.consumer.service.MainService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Api(value = "Main Api", tags = "测试入口")
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

    @ApiOperation(value = "hello", notes = "调用实现在impl")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index(){
        return mainService.hello();
    }

    @RequestMapping(value = "/feign/hello", method = RequestMethod.POST)
    public String feignHello(@RequestParam("name") String name){
        return feignService.hello(name==null?"xm":name);
    }


    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(value = "/feignApi/hello/{name｝", method = RequestMethod.GET)
    public String feignApiHello(@PathVariable("name") String name){
        return feignApiService.hello(name==null?"xx":name);
    }

    private String fallback(){
        return "It's Failed...";
    }
}
