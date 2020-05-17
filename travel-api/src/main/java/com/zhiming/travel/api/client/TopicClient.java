package com.zhiming.travel.api.client;

import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.common.vo.ResultVO;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("travel-topic-service")
public interface TopicClient {
    @GetMapping("/check")
    Result<Boolean> checkTopicsExist(Long[] topics);
}
