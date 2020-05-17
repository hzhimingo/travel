package com.zhiming.travel.api.dto.topic;

import com.zhiming.travel.api.dto.moment.MomentCoverDTO;
import lombok.Data;

import java.util.List;

/**
 * @author HuangZhiMing
 */
@Data
public class TopicCoverDTO {
    private Long topicId;
    private String topicTitle;
    private List<MomentCoverDTO> moments;
    private Integer momentNum;
}
