package com.zhiming.travel.spot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhiming.travel.spot.dto.AMapLocationDTO;
import com.zhiming.travel.spot.dto.AMapSpotDTO;
import com.zhiming.travel.spot.service.AMapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AMapServiceImplTest {

    @Autowired
    private AMapService aMapService;

    @Test
    void obtainLocationByAddress() throws JsonProcessingException {
        AMapLocationDTO locationDTO = aMapService.obtainLocationByAddress("北京市朝阳区阜通东大街6号");
        System.out.println(locationDTO.getLatitude() + " " + locationDTO.getLongitude());
        assertNotNull(locationDTO);
    }

    @Test
    void isSpotExist() throws JsonProcessingException {
        boolean result = aMapService.isSpotExist("B0FFFAB6J2");
        assertTrue(result);
    }

    @Test
    void obtainAMapDTO() throws JsonProcessingException {
        AMapSpotDTO aMapSpotDTO = aMapService.obtainAMapSpot("B0FFFAB6J2");
        System.out.println(aMapSpotDTO);
        assertNotNull(aMapSpotDTO);
    }
}