package com.zhiming.travel.api.query.moment;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MomentCoverQuery {
    @NotNull
    private Integer boundary;
    @NotNull
    private Integer offset;
    private Long userId;
}
