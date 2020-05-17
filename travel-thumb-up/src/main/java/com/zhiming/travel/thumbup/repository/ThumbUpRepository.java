package com.zhiming.travel.thumbup.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ThumbUpRepository {
    int insertNewThumbUp(@Param("userId") Long userId, @Param("serviceBusinessId") Long serviceBusinessId);
    int deleteThumbUp(@Param("userId") Long userId, @Param("serviceBusinessId") Long serviceBusinessId);
    int countThumbUpNum(Long serviceBusinessId);
    int selectIsUserThumbUp(@Param("userId") Long userId, @Param("serviceBusinessId") Long serviceBusinessId);
}