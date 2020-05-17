package com.zhiming.travel.common.exception;

import com.zhiming.travel.common.enums.ResultEnum;
import lombok.Getter;

@Getter
public class TravelServiceException extends RuntimeException {
    private final ResultEnum resultEnum;

    public TravelServiceException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.resultEnum = resultEnum;
    }
}
