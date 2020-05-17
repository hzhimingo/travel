package com.zhiming.travel.spot.repository;

import com.zhiming.travel.spot.domain.SpotPictureDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpotPictureRepository {
    int insertMany(List<SpotPictureDO> records);
    Long selectCoverPictureId(Long spotId);
    List<Long> selectPictureBySpot(Long spotId);
}