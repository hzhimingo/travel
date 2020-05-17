package com.zhiming.travel.spot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhiming.travel.spot.dto.AMapLocationDTO;
import com.zhiming.travel.spot.dto.AMapSpotDTO;
import com.zhiming.travel.spot.service.AMapService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;


/**
 * @author HuangZhiMing
 */
@Service
public class AMapServiceImpl implements AMapService {

    @Value("${amap.key}")
    private String key;

    @Override
    public AMapLocationDTO obtainLocationByAddress(String address) throws JsonProcessingException {
        String url = "https://restapi.amap.com/v3/geocode/geo?key={key}&address={address}";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class, key, address);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(result);
        String location = root.path("geocodes").get(0).get("location").asText();
        String[] locations = location.split(",");
        BigDecimal longitude = new BigDecimal(locations[0]);
        BigDecimal latitude = new BigDecimal(locations[1]);
        AMapLocationDTO locationDTO = new AMapLocationDTO();
        locationDTO.setLongitude(longitude);
        locationDTO.setLatitude(latitude);
        return locationDTO;
    }

    @Override
    public void obtainNearByLocation(BigDecimal latitude, BigDecimal longitude) {

    }

    @Override
    public boolean isSpotExist(String id) throws JsonProcessingException {
        String url = "https://restapi.amap.com/v3/place/detail?key={key}&id={id}";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class, key, id);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(result);
        String count = root.path("count").asText();
        return "1".equals(count);
    }

    @Override
    public AMapSpotDTO obtainAMapSpot(String amapId) throws JsonProcessingException {
        String url = "https://restapi.amap.com/v3/place/detail?key={key}&id={amapId}";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class, key, amapId);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(result);
        AMapSpotDTO aMapSpotDTO = new AMapSpotDTO();
        aMapSpotDTO.setAmapId(ifNotNullToString(root, "id"));
        aMapSpotDTO.setName(ifNotNullToString(root, "name"));
        aMapSpotDTO.setAddress(ifNotNullToString(root, "address"));
        aMapSpotDTO.setProvinceCode(ifNotNullToString(root, "pcode"));
        aMapSpotDTO.setProvinceName(ifNotNullToString(root, "pname"));
        aMapSpotDTO.setCityCode(ifNotNullToString(root, "citycode"));
        aMapSpotDTO.setCityName(ifNotNullToString(root, "cityname"));
        aMapSpotDTO.setAreaCode(ifNotNullToString(root, "adcode"));
        aMapSpotDTO.setAreaName(ifNotNullToString(root, "adname"));
        aMapSpotDTO.setBusinessArea(ifNotNullToString(root, "business_area"));
        String location = root.path("pois").get(0).get("location").asText();
        String[] locations = location.split(",");
        BigDecimal longitude = new BigDecimal(locations[0]);
        BigDecimal latitude = new BigDecimal(locations[1]);
        aMapSpotDTO.setLongitude(longitude);
        aMapSpotDTO.setLatitude(latitude);
        if (root.path("pois").get(0).get("photos") != null) {
            String picture = root.path("pois").get(0).get("photos").get(0).get("url").asText();
            aMapSpotDTO.setPicture(picture);
        }
        return aMapSpotDTO;
    }

    String ifNotNullToString(JsonNode root, String name) {
        JsonNode node = root.path("pois").get(0).get(name);
        if (node == null) {
            return null;
        }
        return node.asText();
    }

}
