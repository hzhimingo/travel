package com.zhiming.travel.qa.repository;

import com.zhiming.travel.qa.domain.QuestionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionRepository {
    int insert(QuestionDO record);
    QuestionDO selectQuestionById(Long questionId);
    List<QuestionDO> selectQuestionCovers(@Param("boundary") int boundary, @Param("offset") int offset, @Param("record") QuestionDO record);
}