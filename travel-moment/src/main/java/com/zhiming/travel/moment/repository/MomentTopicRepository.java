package com.zhiming.travel.moment.repository;

import com.zhiming.travel.moment.domain.MomentTopicDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MomentTopicRepository {
    int insertMomentTopics(List<MomentTopicDO> records);
}