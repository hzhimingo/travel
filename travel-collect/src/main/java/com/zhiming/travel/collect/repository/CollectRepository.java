package com.zhiming.travel.collect.repository;

import com.zhiming.travel.collect.domain.CollectDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CollectRepository {
    int selectCountCollect(Long serviceBusinessId);
    int insertCollect(CollectDO record);
    int deleteCollect(@Param("userId") Long userId, @Param("serviceBusinessId") Long serviceBusinessId);
    int selectIsCollect(@Param("userId") Long userId, @Param("serviceBusinessId") Long serviceBusinessId);
}