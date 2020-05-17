package com.zhiming.travel.api.query.topic;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author HuangZhiMing
 */
@Data
public class TopicCoverQuery {
    @NotNull
    private Integer boundary;
    @NotNull
    private Integer offset;
}
