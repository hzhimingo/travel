package com.zhiming.travel.cr.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * t_comment
 * @author 
 */
@Data
public class CommentDO implements Serializable {
    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论用户id
     */
    private Long author;

    /**
     * 服务业务主键id
     */
    private Long serviceBusinessId;

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