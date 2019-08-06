package com.cloud.consumer.service;


import com.cloud.feign.service.FeignApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("cloud-producer")
public interface FeignApiService extends FeignApi {
}
