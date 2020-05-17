package com.zhiming.travel.spot.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @author HuangZhiMing
 */
@Data
public class RegionDO implements Serializable {
    /**
     * 地区id
     */
    private Integer regionId;

    /**
     * 地区编号
     */
    private Integer regionNo;

    /**
     * 地区名称
     */
    private String regionName;

    /**
     * 地区父区域id
     */
    private Integer parent;

    /**
     * 地区所属国家
     */
    private Integer country;

    /**
     * 地区类型（1-省（直辖市/特别行政区）2-市 3-县（区）4-乡（镇））
     */
    private Integer type;

    private static final long serialVersionUID = 1L;
}