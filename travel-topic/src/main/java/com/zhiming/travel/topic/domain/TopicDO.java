package com.zhiming.travel.topic.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author HuangZhiMing
 */
@Data
public class TopicDO implements Serializable {

    private static final long serialVersionUID = 10L;

    /**
     * 话题id
     */
    private Long topicId;

    /**
     * 话题标题
     */
    private String topicTitle;

    /**
     * 话题介绍
     */
    private String introduction;

    /**
     * 话题背景图
     */
    private String background;

    /**
     * 是否热门（0-否 1-是）
     */
    private Boolean isHot;

    /**
     * 权重
     */
    private Double weight;

    /**
     * 状态（0-下线 1-正常）
     */
    private Integer status;

    /**
     * 是否被删除（0-否 1-是）
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}