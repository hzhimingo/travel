package com.zhiming.travel.sms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhiming.travel.api.form.sms.SendSMSCodeForm;
import com.zhiming.travel.api.form.sms.VerifySMSCodeForm;
import com.zhiming.travel.common.enums.ResultEnum;
import com.zhiming.travel.common.enums.SMSKeyEnum;
import com.zhiming.travel.common.exception.TravelServiceException;
import com.zhiming.travel.common.util.RandomCodeUtil;
import com.zhiming.travel.sms.service.JiGuangSMSService;
import com.zhiming.travel.sms.service.SMSService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SMSServiceImpl implements SMSService {

    private final JiGuangSMSService jiGuangSMSService;
    private final StringRedisTemplate redisTemplate;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public SMSServiceImpl(JiGuangSMSService jiGuangSMSService, StringRedisTemplate redisTemplate, RabbitTemplate rabbitTemplate) {
        this.jiGuangSMSService = jiGuangSMSService;
        this.redisTemplate = redisTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendSMSCode(SendSMSCodeForm form) {
        boolean isKeyExist = containsKey(form.getSmsKey());
        if (!isKeyExist) {
            throw new TravelServiceException(ResultEnum.ILLEGAL_SMS_TYPE_KEY);
        }
        String key = form.getSmsKey() + "_" + form.getTelephone();
        Boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey != null) {
            if (hasKey) {
                throw new TravelServiceException(ResultEnum.SMS_CODE_HAS_NOT_EXPIRED);
            }
        }
        if (form.getDigit() == null) {
            form.setDigit(5);
        }
        String smsCode = RandomCodeUtil.randomNumberCode(form.getDigit());
        Map<String, String> data = new HashMap<>();
        data.put("telephone", form.getTelephone());
        data.put("code", smsCode);
        rabbitTemplate.convertAndSend("sms", data);
        redisTemplate.opsForValue().set(key, smsCode, 5, TimeUnit.MINUTES);
    }

    @Override
    public void verifySMSCode(VerifySMSCodeForm form) {
        boolean isKeyExist = containsKey(form.getSmsKey());
        if (!isKeyExist) {
            throw new TravelServiceException(ResultEnum.ILLEGAL_SMS_TYPE_KEY);
        }
        String key = form.getSmsKey() + "_" + form.getTelephone();
        String storedCheckCode = redisTemplate.opsForValue().get(key);
        if (storedCheckCode == null) {
            throw new TravelServiceException(ResultEnum.SMS_CODE_EXPIRED);
        }
        if (!storedCheckCode.equals(form.getSmsCode())) {
            throw new TravelServiceException(ResultEnum.WRONG_SMS_CODE);
        }
    }

    @Override
    public boolean containsKey(String smsKey) {
        SMSKeyEnum[] enums = SMSKeyEnum.values();
        for (SMSKeyEnum anEnum : enums) {
            if (anEnum.getKey().equals(smsKey)) {
                return true;
            }
        }
        return false;
    }
}
