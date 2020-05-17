package com.zhiming.travel.sms.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public final static String SMS_QUEUE = "sms";

    @Bean
    public Queue smsQueue() {
        return new Queue(SMS_QUEUE);
    }
}
