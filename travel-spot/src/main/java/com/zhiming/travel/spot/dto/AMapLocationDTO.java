package com.zhiming.travel.spot.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AMapLocationDTO {
    private BigDecimal latitude;
    private BigDecimal longitude;
}
