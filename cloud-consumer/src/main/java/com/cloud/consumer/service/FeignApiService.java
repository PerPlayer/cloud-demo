package com.cloud.consumer.service;


import com.cloud.feign.service.FeignApi;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cloud-producer")
public interface FeignApiService extends FeignApi {
}
