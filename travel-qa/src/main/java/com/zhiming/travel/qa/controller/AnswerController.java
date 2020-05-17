package com.zhiming.travel.qa.controller;

import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.dto.qa.AnswerDetailDTO;
import com.zhiming.travel.api.form.qa.PostAnswerForm;
import com.zhiming.travel.api.query.qa.AnswerCoverQuery;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.qa.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/answer")
    public Result<Object> postNewAnswer(PostAnswerForm form) {
        answerService.postNewAnswer(form);
        return ResultUtil.ok();
    }

    @GetMapping("/answer/covers")
    public Result<PageDTO> fetchAnswerCovers(AnswerCoverQuery query, HttpServletRequest request) {
        Long userId = null;
        if (request.getHeader("userId") != null) {
            userId = Long.valueOf(request.getHeader("userId"));
        }
        PageDTO result = answerService.obtainAnswerCovers(userId, query);
        return ResultUtil.ok(result);
    }

    @GetMapping("/answer/{answerId}")
    public Result<AnswerDetailDTO> fetchAnswerDetail(@PathVariable("answerId") Long answerId, HttpServletRequest request) {
        Long userId = null;
        if (request.getHeader("userId") != null) {
            userId = Long.valueOf(request.getHeader("userId"));
        }
        AnswerDetailDTO result = answerService.obtainAnswerDetail(answerId, userId);
        return ResultUtil.ok(result);
    }
}
