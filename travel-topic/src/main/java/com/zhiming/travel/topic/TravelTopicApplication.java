package com.zhiming.travel.topic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("com.zhiming.travel.api.client")
@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.zhiming.travel.topic.repository")
public class TravelTopicApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelTopicApplication.class, args);
    }

}
