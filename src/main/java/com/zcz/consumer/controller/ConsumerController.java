package com.zcz.consumer.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
public class ConsumerController {

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 纯使用eureka和restTemplate去访问服务
     */
    @GetMapping("/consumer")
    public String consumer() {
//        List<InstanceInfo> providers = eurekaClient.getInstancesById("LAPTOP-N0FJ742H:provider:8082");
        String result = "";
        List<InstanceInfo> providers = eurekaClient.getInstancesByVipAddress("provider", false);
        for (InstanceInfo provider : providers) {
            if (provider.getStatus() == InstanceInfo.InstanceStatus.UP) {
                String url = "http://" + provider.getHostName() + ":" + provider.getPort() + "/hello";
                RestTemplate restTemplate = new RestTemplate();
                result = restTemplate.getForObject(url, String.class);
            }
        }
        return result;
    }

    /**
     * ribbon完成客户端的负载均衡，过滤掉了down节点,不指定负载均衡策略，默认是轮询
     */
    @GetMapping("/consumer1")
    public String consumer1() {
        String result = "";
        ServiceInstance provider = loadBalancerClient.choose("provider");
        String url = "http://" + provider.getHost() + ":" + provider.getPort() + "/hello";
        System.out.println(url);
        RestTemplate restTemplate = new RestTemplate();
        result = restTemplate.getForObject(url, String.class);
        return result;
    }

}
