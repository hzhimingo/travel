package com.zhiming.travel.qa.repository;

import com.zhiming.travel.qa.domain.AnswerDO;
import com.zhiming.travel.qa.domain.QuestionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AnswerRepository {

    int selectAnswerCount(Long questionId);

    int deleteByPrimaryKey(Long answerId);

    int insert(AnswerDO record);

    int insertSelective(AnswerDO record);

    AnswerDO selectByPrimaryKey(Long answerId);

    int updateByPrimaryKeySelective(AnswerDO record);

    int updateByPrimaryKey(AnswerDO record);

    AnswerDO selectAnswerByQuestionId(Long questionId);

    List<AnswerDO> selectAnswerCovers(@Param("boundary") Integer boundary, @Param("offset") Integer offset, @Param("record") AnswerDO record);
}