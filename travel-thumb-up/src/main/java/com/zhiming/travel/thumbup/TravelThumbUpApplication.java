package com.zhiming.travel.thumbup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.zhiming.travel.thumbup.repository")
public class TravelThumbUpApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelThumbUpApplication.class, args);
    }

}
