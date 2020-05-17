package com.zhiming.travel.qa.repository;

import com.zhiming.travel.qa.domain.AnswerPictureDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AnswerPictureRepository {
    int insertAnswerPictures(List<AnswerPictureDO> records);
    List<Long> selectPicturesByAnswer(Long answerId);
}