package com.cloud.feign.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by SHW on 2019/6/1.
 */
@RestController
@RequestMapping("/feign/api")
public interface FeignApi {

    @RequestMapping("/hello")
    String hello(@RequestParam("name") String name);
}
