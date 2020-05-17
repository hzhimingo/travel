package com.zhiming.travel.sms.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JiGuangSMSService {
    void sendSMSCode(String telephone, String code) throws JsonProcessingException;
}
