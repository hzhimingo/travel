package com.zhiming.travel.spot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhiming.travel.spot.dto.AMapLocationDTO;
import com.zhiming.travel.spot.dto.AMapSpotDTO;

import java.math.BigDecimal;

/**
 * @author HuangZhiMing
 */
public interface AMapService {
    AMapLocationDTO obtainLocationByAddress(String address) throws JsonProcessingException;
    void obtainNearByLocation(BigDecimal latitude, BigDecimal longitude);
    boolean isSpotExist(String id) throws JsonProcessingException;
    AMapSpotDTO obtainAMapSpot(String amapId) throws JsonProcessingException;
}
