package com.zhiming.travel.api.client;

import com.zhiming.travel.api.dto.user.SimpleUserDTO;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.common.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("travel-user-service")
public interface UserClient {
    @GetMapping("/simple/{userId}")
    Result<SimpleUserDTO> fetchSimpleUserInfo(@PathVariable("userId") Long userId);
}
