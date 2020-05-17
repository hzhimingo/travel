package com.zhiming.travel.moment.repository;

import com.zhiming.travel.moment.domain.MomentPictureDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MomentPictureRepository {
    int insertMomentPictures(List<MomentPictureDO> records);
    Long selectCoverPicture(Long momentId);
    List<Long> selectMomentPicture(Long momentId);
}