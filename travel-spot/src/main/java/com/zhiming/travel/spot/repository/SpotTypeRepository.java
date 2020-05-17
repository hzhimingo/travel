package com.zhiming.travel.spot.repository;

import com.zhiming.travel.api.query.spot.SpotCoverQuery;
import com.zhiming.travel.spot.domain.SpotDO;
import com.zhiming.travel.spot.domain.SpotTypeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpotTypeRepository {

    List<SpotDO> selectSpots(@Param("boundary") Integer boundary, @Param("offset") Integer offset, @Param("query") SpotDO query);

    int isSpotTypeExist(Integer spotTypeId);

    int deleteByPrimaryKey(Integer spotTypeId);

    int insert(SpotTypeDO record);

    int insertSelective(SpotTypeDO record);

    SpotTypeDO selectByPrimaryKey(Integer spotTypeId);

    int updateByPrimaryKeySelective(SpotTypeDO record);

    int updateByPrimaryKey(SpotTypeDO record);
}