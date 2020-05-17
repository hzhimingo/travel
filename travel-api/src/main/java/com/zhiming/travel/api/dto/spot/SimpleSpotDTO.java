package com.zhiming.travel.api.dto.spot;

import lombok.Data;

@Data
public class SimpleSpotDTO {
    private Long spotId;
    private String spotName;
    private String address;
    private Integer cityId;
    private String cityName;
}
