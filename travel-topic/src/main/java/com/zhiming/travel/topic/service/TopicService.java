package com.zhiming.travel.topic.service;

import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.dto.topic.TopicDTO;
import com.zhiming.travel.api.form.topic.PostTopicForm;
import com.zhiming.travel.api.query.topic.TopicCoverQuery;

/**
 * @author HuangZhiMing
 */
public interface TopicService {
    /**
     * @param topicId 话题id
     * @return TopicDTO
     */
    TopicDTO obtainTopicDetail(Long topicId);

    /**
     * @param query 查询
     * @return PageDTO
     */
    PageDTO obtainTopicCovers(TopicCoverQuery query);

    /**
     *
     */
    void postNewTopic(PostTopicForm form);

    /**
     *
     */
    void deleteTopic(Long topicId);

    boolean isTopicsExist(Long[] topics);
}
