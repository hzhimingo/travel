package com.zhiming.travel.cr.service;

import com.zhiming.travel.api.dto.cr.ReplyDTO;
import com.zhiming.travel.api.form.cr.PostReplyForm;

import java.util.List;

/**
 * @author HuangZhiMing
 */
public interface ReplyService {
    void postNewReply(PostReplyForm form);
    List<ReplyDTO> obtainReplyByComment(Long commentId);
}
