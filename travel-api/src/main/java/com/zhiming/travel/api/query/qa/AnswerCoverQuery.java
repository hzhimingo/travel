package com.zhiming.travel.api.query.qa;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
public class AnswerCoverQuery {
    @NonNull
    private Integer boundary;
    @NotNull
    private Integer offset;
    @NotNull
    private Long question;
    private Long userId;
}
