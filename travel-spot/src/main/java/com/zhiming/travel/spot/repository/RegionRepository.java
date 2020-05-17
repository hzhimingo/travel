package com.zhiming.travel.spot.repository;

import com.zhiming.travel.spot.domain.RegionDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RegionRepository {

    String selectNameById(Integer regionId);

    int deleteByPrimaryKey(Integer regionId);

    int insert(RegionDO record);

    int insertSelective(RegionDO record);

    RegionDO selectByPrimaryKey(Integer regionId);

    RegionDO selectByCity(Integer regionId);

    int updateByPrimaryKeySelective(RegionDO record);

    int updateByPrimaryKey(RegionDO record);
}