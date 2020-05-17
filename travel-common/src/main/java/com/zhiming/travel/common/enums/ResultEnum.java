package com.zhiming.travel.common.enums;

/**
 * @author HuangZhiMing
 * 响应错误码枚举类
 */
public enum ResultEnum {

    INTERNAL_SERVER_ERROR(-1, "服务器开小差了，请稍后再试"),
    RESOURCE_NOT_FOUND(-2, "找不到你想要的内容"),
    CAN_NOT_ACCESS(-3, "该资源暂时无法访问"),
    FILE_UPLOAD_FAILED(2000, "文件上传失败"),
    ILLEGAL_SMS_TYPE_KEY(3000, "非法的短信类型值"),
    SMS_CODE_HAS_NOT_EXPIRED(3001, "获取短信验证码过于频繁"),
    SMS_CODE_EXPIRED(3002, "短信验证码已过期"),
    WRONG_SMS_CODE(3003, "短信验证码错误"),
    REGISTER_USER_FAILED(3004, "用户注册失败"),
    REGISTER_FAILED_MOBILE_USED(3005, "注册失败，手机号已经被使用"),
    REGISTER_FAILED_SMS_CODE_WRONG(3006, "注册失败，短信验证码错误"),
    SPOT_TYPE_NOT_EXIST(4000, "地点类型不存在"),
    CREATE_SPOT_FAILED(4001, "地点创建失败");

    private final Integer code;
    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
