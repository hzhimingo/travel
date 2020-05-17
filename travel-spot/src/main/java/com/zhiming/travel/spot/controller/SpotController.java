package com.zhiming.travel.spot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.dto.spot.SimpleSpotDTO;
import com.zhiming.travel.api.dto.spot.SpotDTO;
import com.zhiming.travel.api.dto.spot.SpotLocationDTO;
import com.zhiming.travel.api.form.spot.PostSpotForm;
import com.zhiming.travel.api.form.spot.UpdateSpotForm;
import com.zhiming.travel.api.query.spot.SpotCoverQuery;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.spot.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpotController {

    private final SpotService spotService;

    @Autowired
    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }

    @GetMapping("/covers")
    public Result<PageDTO> fetchSpotCovers(SpotCoverQuery query) {
        PageDTO result = spotService.obtainSpotCovers(null, query);
        return ResultUtil.ok(result);
    }

    @GetMapping("/{spotId}")
    public Result<SpotDTO> fetchSpotDetail(@PathVariable("spotId") Long spotId) throws JsonProcessingException {
        SpotDTO result = spotService.obtainSpot(spotId);
        return ResultUtil.ok(result);
    }

    @PostMapping("/")
    public Result<Object> postNewSpot(PostSpotForm form) throws JsonProcessingException {
        spotService.postNewSpot(form);
        return ResultUtil.ok();
    }

    @PutMapping("/")
    public Result<Object> updateSpot(UpdateSpotForm form) {
        spotService.updateSpot(form);
        return ResultUtil.ok();
    }

    @GetMapping("/check/{spotId}")
    public Result<Boolean> checkSpotExist(@PathVariable("spotId") Long spotId) {
        boolean result = spotService.checkSpotExist(spotId);
        return ResultUtil.ok(result);
    }

    @GetMapping("/point/{spotId}")
    public Result<SpotLocationDTO> obtainSpotPoint(@PathVariable("spotId") Long spotId) {
        SpotLocationDTO result = spotService.obtainSpotLocation(spotId);
        return ResultUtil.ok(result);
    }

    @GetMapping("/spotName/{spotId}")
    public Result<String> fetchSpotName(@PathVariable("spotId") Long spotId) {
        String spotName = spotService.obtainSpotName(spotId);
        return ResultUtil.ok(spotName);
    }

    @GetMapping("/simple")
    public Result<List<SimpleSpotDTO>> fetchSimpleSpot(Integer city) {
        List<SimpleSpotDTO> result = spotService.obtainSimpleSpots(city);
        return ResultUtil.ok(result);
    }
}
