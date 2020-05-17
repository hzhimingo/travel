package com.zhiming.travel.moment.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * t_moment_topic
 * @author 
 */
@Data
public class MomentTopicDO implements Serializable {
    private Long momentId;

    private Long topicId;

    private static final long serialVersionUID = 1L;
}