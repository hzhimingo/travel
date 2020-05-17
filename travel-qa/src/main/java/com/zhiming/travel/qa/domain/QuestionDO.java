package com.zhiming.travel.qa.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * t_question
 * @author 
 */
@Data
public class QuestionDO implements Serializable {
    /**
     * 问题id
     */
    private Long questionId;

    /**
     * 问题标题
     */
    private String title;

    /**
     * 问题内容
     */
    private String content;

    /**
     * 发布问题用户id
     */
    private Long author;

    /**
     * 是否热门（0-否 1-是）
     */
    private Integer isHot;

    /**
     * 权重
     */
    private Double weight;

    /**
     * 状态（0-正常 1-封禁）
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

    private static final long serialVersionUID = 1L;
}