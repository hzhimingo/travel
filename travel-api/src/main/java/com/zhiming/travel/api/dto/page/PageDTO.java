package com.zhiming.travel.api.dto.page;

import lombok.Data;

/**
 * @author HuangZhiMing
 */
@Data
public class PageDTO {
    private Integer boundary;
    private Integer offset;
    private Object data;
    private Boolean hasNext;
}
