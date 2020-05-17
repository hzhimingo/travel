package com.zhiming.travel.api.client;

import com.zhiming.travel.api.form.sms.SendSMSCodeForm;
import com.zhiming.travel.api.form.sms.VerifySMSCodeForm;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.common.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("travel-sms-service")
public interface SMSClient {
    @PostMapping("/")
    Result<Object> sendSMSCode(@RequestBody SendSMSCodeForm form);

    @PostMapping("/verify")
    Result<Object> verifySMSCode(@RequestBody VerifySMSCodeForm form);
}
