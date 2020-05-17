package com.zhiming.travel.api.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("travel-qa-service")
public interface QAClient {
}
