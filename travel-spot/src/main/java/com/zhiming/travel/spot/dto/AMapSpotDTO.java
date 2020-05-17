package com.zhiming.travel.spot.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AMapSpotDTO {
    private String amapId;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String businessArea;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String areaCode;
    private String areaName;
    private String picture;
}
