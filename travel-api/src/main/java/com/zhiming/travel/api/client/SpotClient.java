package com.zhiming.travel.api.client;

import com.zhiming.travel.api.dto.spot.SpotLocationDTO;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.common.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("travel-spot-service")
public interface SpotClient {
    @GetMapping("/check/{spotId}")
    Result<Boolean> isSpotExist(@PathVariable("spotId") Long spotId);
    @GetMapping("/point/{spotId}")
    Result<SpotLocationDTO> obtainSpotPoint(@PathVariable("spotId") Long spotId);
    @GetMapping("/spotName/{spotId}")
    Result<String> obtainSpotName(@PathVariable("spotId") Long spotId);
}
