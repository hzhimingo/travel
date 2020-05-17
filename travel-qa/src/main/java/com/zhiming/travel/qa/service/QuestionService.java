package com.zhiming.travel.qa.service;

import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.dto.qa.QuestionCoverDTO;
import com.zhiming.travel.api.dto.qa.QuestionDetailDTO;
import com.zhiming.travel.api.form.qa.PostQuestionForm;
import com.zhiming.travel.api.query.qa.QuestionCoverQuery;

import java.util.List;

public interface QuestionService {
    void postNewQuestion(PostQuestionForm form);
    PageDTO obtainQuestionCovers(QuestionCoverQuery query);
    QuestionDetailDTO obtainQuestionDetail(Long questionId, Long userId);
}
