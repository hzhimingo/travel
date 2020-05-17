package com.zhiming.travel.api.dto.topic;

import lombok.Data;

@Data
public class TopicDTO {
    private Long topicId;
    private String topicTitle;
    private String introduction;
    private String background;
}
