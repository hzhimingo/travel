package com.zhiming.travel.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableEurekaClient
@SpringBootApplication
public class TravelSmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelSmsApplication.class, args);
    }

}
