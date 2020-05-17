package com.zhiming.travel.topic.controller;

import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.dto.topic.TopicDTO;
import com.zhiming.travel.api.form.topic.PostTopicForm;
import com.zhiming.travel.api.query.topic.TopicCoverQuery;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/{topicId}")
    public Result<TopicDTO> fetchTopic(@PathVariable("topicId") Long topicId) {
        TopicDTO result = topicService.obtainTopicDetail(topicId);
        return ResultUtil.ok(result);
    }

    @GetMapping("/covers")
    public Result<PageDTO> fetchTopicCovers(TopicCoverQuery query) {
        PageDTO result = topicService.obtainTopicCovers(query);
        return ResultUtil.ok(result);
    }

    @GetMapping("/check")
    public Result checkTopicsExist(Long[] topics) {
        return ResultUtil.ok();
    }

    @PostMapping
    public Result postNewTopic(PostTopicForm form) {
        topicService.postNewTopic(form);
        return ResultUtil.ok();
    }
}
