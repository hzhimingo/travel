package com.zhiming.travel.moment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("com.zhiming.travel.api.client")
@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.zhiming.travel.moment.repository")
public class TravelMomentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelMomentApplication.class, args);
    }

}
