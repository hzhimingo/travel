package com.zhiming.travel.api.query.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author HuangZhiMing
 */
@Data
public class UserInfosQuery {
    @NotNull(message = "缺少边界参数")
    private Integer boundary;
    @NotNull(message = "缺少偏移量参数")
    private Integer offset;
    private Integer gender;
    private Integer level;
    private Integer status;
}
