package com.zhiming.travel.spot.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author HuangZhiMing
 */
@Data
public class SpotTypeDO implements Serializable {
    /**
     * 地点类型id
     */
    private Integer spotTypeId;

    /**
     * 地点类型名称
     */
    private String typeName;

    /**
     * 状态（0-不可用 1-可用）
     */
    private Integer status;

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