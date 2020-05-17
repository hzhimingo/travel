package com.zhiming.travel.sms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;;
import com.zhiming.travel.sms.service.JiGuangSMSService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class JiGuangSMSServiceImpl implements JiGuangSMSService {

    @Value("${jiguang.appKey}")
    private String appKey;

    @Value("${jiguang.masterSecret}")
    private String masterSecret;

    @Override
    public void sendSMSCode(String telephone, String code) throws JsonProcessingException {
        String url = "https://api.sms.jpush.cn/v1/messages";
        HttpHeaders headers = new HttpHeaders();
        String key = appKey.concat(":").concat(masterSecret);
        String encodedKey = Base64.getEncoder().encodeToString(key.getBytes());
        headers.add("Authorization","Basic " + encodedKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HashMap<String, Object> body = new LinkedHashMap<>();
        HashMap<String, String> tempArea  = new HashMap<>();
        tempArea.put("code", code);
        body.put("mobile", telephone);
        body.put("sign_id", 13715);
        body.put("temp_id", 179319);
        body.put("temp_para", tempArea);
        System.out.println(body);
        HttpEntity<HashMap> entity = new HttpEntity<HashMap>(body, headers);
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.postForEntity(url, entity, String.class);
        System.out.println(response.getBody());
    }
}
