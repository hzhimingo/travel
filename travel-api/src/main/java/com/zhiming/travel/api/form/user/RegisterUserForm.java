package com.zhiming.travel.api.form.user;

import lombok.Data;

/**
 * @author HuangZhiMing
 */
@Data
public class RegisterUserForm {
    /**
     * telephone 用户手机号码
     */
    private String telephone;
    /**
     * password 用户注册时填写的密码
     */
    private String password;
    /**
     * smsCode 用户填写的短信验证码
     */
    private String smsCode;
}
