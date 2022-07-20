package com.zcz.consumer.service;

import com.zcz.consumer.component.TestFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient(name="provider",fallback = TestFeignServiceFallback.class)
public interface TestFeignService {

    @GetMapping("/hello")
    public String hello();
}
