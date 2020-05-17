package com.zhiming.travel.sms.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhiming.travel.sms.service.JiGuangSMSService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SMSSendListener {

    private final JiGuangSMSService jiGuangSMSService;

    @Autowired
    public SMSSendListener(JiGuangSMSService jiGuangSMSService) {
        this.jiGuangSMSService = jiGuangSMSService;
    }

    @RabbitHandler
    public void sendSMS(Map<String, String> data) {
        try {
            jiGuangSMSService.sendSMSCode(data.get("telephone"), data.get("code"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
