package com.zhiming.travel.cr.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * t_reply
 * @author 
 */
@Data
public class ReplyDO implements Serializable {
    /**
     * 回复id
     */
    private Long replyId;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 回复作者id
     */
    private Long author;

    /**
     * 被回复用户id
     */
    private Long replyTo;

    /**
     * 所属评论id
     */
    private Long comment;

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