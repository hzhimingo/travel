package com.zhiming.travel.api.client;

import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.common.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author HuangZhiMing
 * Over
 */
@FeignClient("travel-thumbUp-service")
public interface ThumbUpClient {
    @GetMapping("/count/{serviceBusinessId}")
    Result<Integer> fetchThumbUpNum(@PathVariable("serviceBusinessId") Long serviceBusinessId);
    @GetMapping("/isThumbUp")
    Result<Boolean> isThumbUp(@RequestParam("userId") Long userId, @RequestParam("serviceBusinessId") Long serviceBusinessId);
}
