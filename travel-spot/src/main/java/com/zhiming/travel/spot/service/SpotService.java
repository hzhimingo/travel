package com.zhiming.travel.spot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.dto.spot.SimpleSpotDTO;
import com.zhiming.travel.api.dto.spot.SpotDTO;
import com.zhiming.travel.api.dto.spot.SpotLocationDTO;
import com.zhiming.travel.api.form.spot.PostSpotForm;
import com.zhiming.travel.api.form.spot.UpdateSpotForm;
import com.zhiming.travel.api.query.spot.SpotCoverQuery;
import com.zhiming.travel.spot.dto.AMapSpotDTO;

import java.util.List;

/**
 * @author HuangZhiMing
 */
public interface SpotService {
    /**
     * @param spotId 地点id
     * @return SpotDTO
     * 获取单个地点详细信息
     */
    SpotDTO obtainSpot(Long spotId) throws JsonProcessingException;

    /**
     * @param query 查询
     * @return PageDTO
     * 获取地点简略信息列表
     */
    PageDTO obtainSpotCovers(Long userId, SpotCoverQuery query);

    /**
     * @param form 地点表单
     * 创建新的地点
     */
    void postNewSpot(PostSpotForm form) throws JsonProcessingException;

    /**
     * @param form 更新地点表单
     * 更新地点
     */
    void updateSpot(UpdateSpotForm form);

    boolean checkSpotExist(Long spotId);

    SpotLocationDTO obtainSpotLocation(Long spotId);

    String obtainSpotName(Long spotId);

    List<SimpleSpotDTO> obtainSimpleSpots(Integer city);
}
