package com.zhiming.travel.qa.service;

import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.dto.qa.AnswerCoverDTO;
import com.zhiming.travel.api.dto.qa.AnswerDetailDTO;
import com.zhiming.travel.api.dto.qa.AnswerInnerDTO;
import com.zhiming.travel.api.form.qa.PostAnswerForm;
import com.zhiming.travel.api.query.qa.AnswerCoverQuery;

import java.util.List;

public interface AnswerService {
    void postNewAnswer(PostAnswerForm form);
    AnswerInnerDTO obtainAnswerInners(Long questionId);
    PageDTO obtainAnswerCovers(Long userId, AnswerCoverQuery query);
    AnswerDetailDTO obtainAnswerDetail(Long answerId, Long userId);
    int obtainAnswerCount(Long questionId);
}
