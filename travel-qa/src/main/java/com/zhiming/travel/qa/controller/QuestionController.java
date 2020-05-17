package com.zhiming.travel.qa.controller;

import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.dto.qa.QuestionDetailDTO;
import com.zhiming.travel.api.form.qa.PostQuestionForm;
import com.zhiming.travel.api.query.qa.QuestionCoverQuery;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.qa.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/question")
    public Result<Object> postNewQuestion(PostQuestionForm form) {
        questionService.postNewQuestion(form);
        return ResultUtil.ok();
    }

    @GetMapping("/question/covers")
    public Result<PageDTO> fetchQuestionCovers(QuestionCoverQuery query) {
        PageDTO result = questionService.obtainQuestionCovers(query);
        return ResultUtil.ok(result);
    }

    @GetMapping("/question/{questionId}")
    public Result<QuestionDetailDTO> fetchQuestionDetail(@PathVariable("questionId") Long questionId, HttpServletRequest request) {
        Long userId = null;
        if (request.getHeader("userId") != null) {
            userId = Long.valueOf(request.getHeader("userId"));
        }
        QuestionDetailDTO result = questionService.obtainQuestionDetail(questionId, userId);
        return ResultUtil.ok(result);
    }
}
