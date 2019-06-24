package com.cloud.config.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class MainController {

    @RequestMapping("/hello")
    public String hello() {
        return "hi, server";
    }
}
