package com.zhiming.travel.topic.repository;

import com.zhiming.travel.topic.domain.TopicDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TopicRepository {

    TopicDO selectTopicById(Long topicId);

    int insertNewTopic(TopicDO record);

    int isTopicExist(Long topicId);

    int deleteByPrimaryKey(Long topicId);

    int isTopicsExist(Long[] topics);

    List<TopicDO> selectTopicCover(@Param("boundary") int boundary, @Param("offset") int offset, @Param("record") TopicDO record);
}