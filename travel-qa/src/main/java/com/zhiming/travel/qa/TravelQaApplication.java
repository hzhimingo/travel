package com.zhiming.travel.qa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableFeignClients("com.zhiming.travel.api.client")
@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.zhiming.travel.qa.repository")
public class TravelQaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelQaApplication.class, args);
    }

}
