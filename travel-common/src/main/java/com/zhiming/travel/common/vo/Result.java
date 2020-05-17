package com.zhiming.travel.common.vo;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
}
