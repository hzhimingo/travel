package com.zhiming.travel.cr.repository;

import com.zhiming.travel.cr.domain.CommentDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentRepository {
    int insertNewComment(CommentDO record);
    List<CommentDO> selectCommentByServiceBusinessId(Long serviceBusinessId);
    int selectCommentCount(Long serviceBusinessId);
}