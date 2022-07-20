package com.zcz.consumer.component;

import com.zcz.consumer.service.TestFeignService;
import org.springframework.stereotype.Service;

@Service("testFeignServiceFallback")
public class TestFeignServiceFallback implements TestFeignService {

    @Override
    public String hello() {
        return "出错了，访问不了!";
    }
}
