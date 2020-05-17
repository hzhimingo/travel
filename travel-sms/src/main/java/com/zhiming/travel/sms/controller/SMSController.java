package com.zhiming.travel.sms.controller;

import com.zhiming.travel.api.form.sms.SendSMSCodeForm;
import com.zhiming.travel.api.form.sms.VerifySMSCodeForm;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.common.vo.ResultVO;
import com.zhiming.travel.sms.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangZhiMing
 */
@RestController
public class SMSController {

    private final SMSService smsService;

    @Autowired
    public SMSController(SMSService smsService) {
        this.smsService = smsService;
    }

    @PostMapping()
    public Result<Object> sendSMSCode(@RequestBody SendSMSCodeForm form) {
        smsService.sendSMSCode(form);
        return ResultUtil.ok();
    }

    @PostMapping("/verify")
    public Result<Object> verifySMSCode(@RequestBody VerifySMSCodeForm form) {
        smsService.verifySMSCode(form);
        return ResultUtil.ok();
    }
}
