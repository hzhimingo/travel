package com.zhiming.travel.api.query.spot;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author HuangZhiMing
 */
@Data
public class SpotCoverQuery {
    @NotNull
    private Integer boundary;
    @NotNull
    private Integer offset;
    private Integer type;
    private Integer area;
    private Integer sortType;
    private Long userId;
}
