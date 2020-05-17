package com.zhiming.travel.common.enums;

/**
 * @author HuangZhiMing
 */
public enum SMSKeyEnum {
    USER_REGISTER_CODE("USER_REGISTER"),
    USER_SIGN_IN("USER_SIGN_IN");

    private final String key;

    SMSKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
