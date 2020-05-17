package com.zhiming.travel.api.form.sms;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author HuangZhiMing
 */
@Data
public class VerifySMSCodeForm {

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
     * smsCode 短信验证码
     */
    @NotNull
    private String smsCode;
}
