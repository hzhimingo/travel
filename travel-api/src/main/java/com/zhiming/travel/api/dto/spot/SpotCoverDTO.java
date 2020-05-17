package com.zhiming.travel.api.dto.spot;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpotCoverDTO {
    private Long spotId;
    private String spotName;
    private String cover;
    private String keywords;
    private Integer score;
    private Boolean isStar;
    private Integer starNum;
    private String area;
}
