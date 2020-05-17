package com.zhiming.travel.spot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients("com.zhiming.travel.api.client")
@EnableEurekaClient
@EnableTransactionManagement
@SpringBootApplication
public class TravelSpotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelSpotApplication.class, args);
    }

}
