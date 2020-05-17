package com.zhiming.travel.moment.repository;

import com.zhiming.travel.moment.domain.MomentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MomentRepository {
    int insertNewMoment(MomentDO record);
    int updateMomentStatusToDisable(Long momentId);
    int updateMomentStatusToNormal(Long momentId);
    int deleteMomentById(Long momentId);
    int updateMomentInfo(MomentDO record);
    int isMomentExist(Long momentId);
    MomentDO selectMomentById(Long momentId);
    List<MomentDO> selectMomentCover(@Param("boundary") int boundary, @Param("offset") int offset, @Param("record") MomentDO record);
}