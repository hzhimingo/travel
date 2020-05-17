package com.zhiming.travel.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class TravelOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelOauthApplication.class, args);
    }

}
