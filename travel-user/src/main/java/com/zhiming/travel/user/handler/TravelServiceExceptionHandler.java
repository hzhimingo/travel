package com.zhiming.travel.user.handler;

import com.zhiming.travel.common.enums.ResultEnum;
import com.zhiming.travel.common.exception.TravelServiceException;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.common.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TravelServiceExceptionHandler {

    @ExceptionHandler(value = TravelServiceException.class)
    public Result handleTravelServiceException(TravelServiceException e) {
        return ResultUtil.error(e.getResultEnum());
    }

    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception e) {
        return ResultUtil.error(ResultEnum.INTERNAL_SERVER_ERROR);
    }
}
