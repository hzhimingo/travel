package com.zhiming.travel.spot.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * @author HuangZhiMing
 */
@Data
public class CountryDO implements Serializable {
    /**
     * 国家id
     */
    private Integer countryId;

    /**
     * 国家编号
     */
    private Integer countryNo;

    /**
     * 国家名称
     */
    private String chineseName;

    /**
     * 国家英文名称
     */
    private String englishName;

    /**
     * 国家数字代号
     */
    private String countryCode;

    /**
     * 国家英文全称
     */
    private String englishOriginName;

    /**
     * 电话区号
     */
    private Integer phoneAreaCode;

    private static final long serialVersionUID = 1L;
}