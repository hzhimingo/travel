package com.zhiming.travel.spot.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author HuangZhiMing
 */
@Data
public class SpotDO implements Serializable {
    /**
     * 地点id
     */
    private Long spotId;

    /**
     * 地点名称
     */
    private String spotName;

    /**
     * 地点类型
     */
    private Integer type;

    /**
     * 地点所在国家
     */
    private Integer country;

    /**
     * 地点所在省级区划
     */
    private Integer province;

    /**
     * 地点所在城市
     */
    private Integer city;

    /**
     * 地点所在区域
     */
    private Integer area;


    /**
     * 地点介绍
     */
    private String introduction;

    /**
     * 地点详细地址
     */
    private String address;

    /**
     * 地点经度
     */
    private BigDecimal latitude;

    /**
     * 地点纬度
     */
    private BigDecimal longitude;

    /**
     * 是否热门（0-否 1-是）
     */
    private Integer isHot;

    /**
     * 状态（0-正常 1-审核）
     */
    private Integer status;

    private Integer score;

    private String keywords;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}