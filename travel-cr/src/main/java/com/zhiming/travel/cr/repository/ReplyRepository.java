package com.zhiming.travel.cr.repository;

import com.zhiming.travel.cr.domain.ReplyDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReplyRepository {
    int insertNewReply(ReplyDO record);
    List<ReplyDO> selectReplyByCommentId(Long commentId);
}