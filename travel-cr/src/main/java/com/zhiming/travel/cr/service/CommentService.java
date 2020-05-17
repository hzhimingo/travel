package com.zhiming.travel.cr.service;

import com.zhiming.travel.api.dto.cr.CommentCoverDTO;
import com.zhiming.travel.api.dto.cr.CommentDTO;
import com.zhiming.travel.api.form.cr.PostCommentForm;

import java.util.List;

/**
 * @author HuangZhiMing
 */
public interface CommentService {
    void postNewComment(PostCommentForm form);
    List<CommentCoverDTO> obtainCommentCover(Long serviceBusinessId);
    List<CommentDTO> obtainComments(Long serviceBusinessId);
    int obtainCommentCount(Long serviceBusinessId);
}
