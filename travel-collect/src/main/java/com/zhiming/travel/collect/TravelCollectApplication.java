package com.zhiming.travel.collect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TravelCollectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelCollectApplication.class, args);
    }

}
