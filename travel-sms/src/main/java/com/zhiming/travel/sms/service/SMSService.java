package com.zhiming.travel.sms.service;


import com.zhiming.travel.api.form.sms.SendSMSCodeForm;
import com.zhiming.travel.api.form.sms.VerifySMSCodeForm;

/**
 * @author HuangZhiMing
 */
public interface SMSService {
    /**
     * @param form 发送短信表单
     */
    void sendSMSCode(SendSMSCodeForm form);

    /**
     * @param form 短信验证表单
     */
    void verifySMSCode(VerifySMSCodeForm form);

    boolean containsKey(String smsKey);
}
