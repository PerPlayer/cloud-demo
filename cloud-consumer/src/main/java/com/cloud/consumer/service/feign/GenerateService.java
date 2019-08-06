package com.cloud.consumer.service.feign;


import com.cloud.consumer.utils.B;
import com.cloud.consumer.utils.PQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("cloud-producer")
@RequestMapping("/api/generate/")
public interface GenerateService {

    @RequestMapping("/list")
    B list(@RequestBody PQuery<String> query);

    @RequestMapping("/code")
    byte[] code(@RequestBody PQuery<String> query);
}
