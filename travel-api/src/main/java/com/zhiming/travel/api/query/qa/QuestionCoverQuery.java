package com.zhiming.travel.api.query.qa;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QuestionCoverQuery {
    @NotNull
    private Integer boundary;
    @NotNull
    private Integer offset;
}
