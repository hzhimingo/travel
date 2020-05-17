package com.zhiming.travel.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author HuangZhiMing
 */

@EnableFeignClients("com.zhiming.travel.api.client")
@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.zhiming.travel.user.repository")
public class TravelUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelUserApplication.class, args);
    }

}
