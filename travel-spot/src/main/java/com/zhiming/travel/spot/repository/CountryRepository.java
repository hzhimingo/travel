package com.zhiming.travel.spot.repository;

import com.zhiming.travel.spot.domain.CountryDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CountryRepository {

    String selectNameById(Integer countryId);

    int deleteByPrimaryKey(Integer countryId);

    int insert(CountryDO record);

    int insertSelective(CountryDO record);

    CountryDO selectByPrimaryKey(Integer countryId);

    int updateByPrimaryKeySelective(CountryDO record);

    int updateByPrimaryKey(CountryDO record);
}