package com.zhiming.travel.common.vo;

import lombok.Data;

/**
 * @author HuangZhiMing
 */
@Data
public class ResultVO {
    /**
     * code 错误码, 没有错误返回0
     */
    private Integer code;
    /**
     * message 描述
     */
    private String message;
    /**
     * data 返回的数据
     */
    private Object data;
}
