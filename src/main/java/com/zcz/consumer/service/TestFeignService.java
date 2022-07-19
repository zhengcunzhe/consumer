package com.zcz.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient("provider")
public interface TestFeignService {

    @GetMapping("/hello")
    public String hello();
}
