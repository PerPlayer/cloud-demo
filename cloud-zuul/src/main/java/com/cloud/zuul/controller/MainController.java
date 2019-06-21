package com.cloud.zuul.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    @RequestMapping("/main/hello")
    public String hello() {
        return "hi, zuul";
    }

    @RequestMapping("/bye")
    public String bye() {
        return "bye, zuul";
    }
}
