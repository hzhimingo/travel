package com.zhiming.travel.spot.repository;

import com.zhiming.travel.spot.domain.SpotDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpotRepository {
    int isSpotExist(Long spotId);
    int insertNewSpot(SpotDO record);
    int updateSpot(SpotDO record);
    SpotDO selectBySpotId(Long spotId);
    String selectSpotName(Long spotId);
    List<SpotDO> selectSpot(@Param("boundary") Integer boundary, @Param("offset") Integer offset, @Param("query") SpotDO query);
    List<SpotDO> selectSpotByCity(Integer cityId);
}
