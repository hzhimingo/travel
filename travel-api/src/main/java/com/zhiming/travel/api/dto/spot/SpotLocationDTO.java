package com.zhiming.travel.api.dto.spot;

import lombok.Data;

@Data
public class SpotLocationDTO {
    private Integer countryId;
    private String countryName;
    private Integer province;
    private String provinceName;
    private Integer city;
    private String cityName;
    private Integer area;
    private String areaName;
    private Long spotId;
    private String spotName;
}
