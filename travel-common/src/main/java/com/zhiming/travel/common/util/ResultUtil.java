package com.zhiming.travel.common.util;

import com.zhiming.travel.common.enums.ResultEnum;
import com.zhiming.travel.common.exception.CallServiceException;
import com.zhiming.travel.common.vo.Result;

public class ResultUtil {

    private static <T> Result<T> response(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    public static <T> Result<T> ok() {
        return response(0, "ok", null);
    }

    public static <T> Result<T> ok(String msg, T data) {
        return response(0, msg, data);
    }

    public static <T> Result<T> ok(T data) {
        return response(0, "ok", data);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return response(code, msg, null);
    }

    public static <T> Result<T> error(ResultEnum resultEnum) {
        return response(resultEnum.getCode(), resultEnum.getMessage(), null);
    }

    public static <T> T parsingData(Result<T> data) {
        if (data.getCode() != 0) {
            throw new CallServiceException(data.getCode() + ":" +data.getMsg());
        }
        return data.getData();
    }
}
