package com.zhiming.travel.api.form.sms;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SendSMSCodeForm {

    /**
     * smsKey 短信验证key
     */
    @NotNull
    private String smsKey;

    /**
     * telephone 手机号码
     */
    @NotNull
    private String telephone;

    /**
     * digit 短信验证码位数
     */
    private Integer digit;
}
