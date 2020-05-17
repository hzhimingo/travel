package com.zhiming.travel.collect.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_collect
 * @author 
 */
@Data
public class CollectDO implements Serializable {
    /**
     * 收藏id
     */
    private Long collectId;

    /**
     * 收藏用户id
     */
    private Long collectUser;

    /**
     * 服务id
     */
    private Integer service;

    /**
     * 服务业务主键id
     */
    private Long serviceBusinessId;

    /**
     * 收藏时间
     */
    private Date createTime;

    /**
     * (1-瞬间 2-地点 3-问题 4-回答)
     */
    private Integer type;

    private static final long serialVersionUID = 1L;
}