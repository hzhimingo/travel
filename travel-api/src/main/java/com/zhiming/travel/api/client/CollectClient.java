package com.zhiming.travel.api.client;

import com.zhiming.travel.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author HuangZhiMing
 * Over
 */
@FeignClient("travel-collect-service")
public interface CollectClient {
    @GetMapping("/count/{id}")
    Result<Integer> fetchCollectCount(@PathVariable("id") Long id);
    @GetMapping("/isCollect")
    Result<Boolean> isCollect(@RequestParam("userId") Long userId, @RequestParam("serviceBusinessId") Long serviceBusinessId);
}
