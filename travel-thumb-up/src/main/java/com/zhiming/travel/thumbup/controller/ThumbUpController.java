package com.zhiming.travel.thumbup.controller;

import com.zhiming.travel.api.form.thumbup.PostThumbUpForm;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.thumbup.service.ThumbUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ThumbUpController {

    private final ThumbUpService thumbUpService;

    @Autowired
    public ThumbUpController(ThumbUpService thumbUpService) {
        this.thumbUpService = thumbUpService;
    }

    @PostMapping("/")
    public Result<Object> thumbUp(PostThumbUpForm form) {
        System.out.println(form.getUserId());
        System.out.println(form.getServiceBusinessId());
        thumbUpService.thumbUp(form.getUserId(), form.getServiceBusinessId());
        return ResultUtil.ok();
    }

    @DeleteMapping("/cancel")
    public Result<Object> cancelThumbUp(Long userId, Long serviceBusinessId) {
        thumbUpService.cancelThumbUp(userId, serviceBusinessId);
        return ResultUtil.ok();
    }

    @GetMapping("/count/{serviceBusinessId}")
    public Result<Integer> fetchThumbUpNum(@PathVariable("serviceBusinessId") Long serviceBusinessId) {
        int count = thumbUpService.obtainThumbUpNum(serviceBusinessId);
        return ResultUtil.ok(count);
    }

    @GetMapping("/isThumbUp")
    public Result<Boolean> isThumbUp(Long userId, Long serviceBusinessId) {
        Boolean isThumbUp = thumbUpService.isThumbUp(userId, serviceBusinessId);
        return ResultUtil.ok(isThumbUp);
    }
}
