package com.zhiming.travel.spot.handler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TravelServiceExceptionHandler {

//    @ExceptionHandler(value = TravelServiceException.class)
//    public ResultVO handleTravelServiceException(TravelServiceException e) {
//        return ResultUtil.error(e.getResultEnum());
//    }
//
//    @ExceptionHandler(value = Exception.class)
//    public ResultVO handleException(Exception e) {
//        return ResultUtil.error(ResultEnum.INTERNAL_SERVER_ERROR);
//    }
}