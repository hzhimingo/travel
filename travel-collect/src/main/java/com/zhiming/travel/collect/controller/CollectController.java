package com.zhiming.travel.collect.controller;

import com.zhiming.travel.api.form.collect.PostCollectForm;
import com.zhiming.travel.collect.service.CollectService;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CollectController {

    private final CollectService collectService;

    @Autowired
    public CollectController(CollectService collectService) {
        this.collectService = collectService;
    }

    @GetMapping("/count/{id}")
    public Result<Integer> fetchCollectCount(@PathVariable("id") Long id) {
        int count = collectService.obtainCollectCount(id);
        return ResultUtil.ok(count);
    }

    @PostMapping
    public Result<Object> postNewCollect(PostCollectForm form) {
        collectService.collect(form);
        return ResultUtil.ok();
    }

    @DeleteMapping
    public Result<Object> deleteCollect(Long userId, Long serviceBusinessId) {
        collectService.cancelCollect(userId, serviceBusinessId);
        return ResultUtil.ok();
    }

    @GetMapping("/isCollect")
    public Result<Boolean> isCollect(Long userId, Long serviceBusinessId) {
        Boolean isCollect = collectService.isCollect(userId, serviceBusinessId);
        return ResultUtil.ok(isCollect);
    };
}
