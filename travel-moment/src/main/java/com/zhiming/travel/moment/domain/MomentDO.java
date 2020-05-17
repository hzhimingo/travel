package com.zhiming.travel.moment.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author HuangZhiMing
 */
@Data
public class MomentDO implements Serializable {
    /**
     * 瞬间id
     */
    private Long momentId;

    /**
     * 瞬间标题
     */
    private String title;

    /**
     * 瞬间内容
     */
    private String content;

    /**
     * 作者id
     */
    private Long author;

    /**
     * 瞬间所在国家
     */
    private Integer country;

    /**
     * 瞬间所在城市
     */
    private Integer city;

    /**
     * 瞬间关联地点
     */
    private Long spot;

    /**
     * 状态（0-正常 1-草稿 2-封禁）
     */
    private Integer status;

    /**
     * 是否被删除（0-否 1-是）
     */
    private Integer isDelete;

    /**
     * 是否热门（0-否 1-是）
     */
    private Integer isHot;

    /**
     * 浏览人数
     */
    private Integer visitedNum;

    /**
     * 权重
     */
    private Double weight;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 5L;
}