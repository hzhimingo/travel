package com.zhiming.travel.moment.controller;

import com.zhiming.travel.api.dto.moment.MomentDetailDTO;
import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.form.moment.PostMomentForm;
import com.zhiming.travel.api.query.moment.MomentCoverQuery;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.moment.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@RestController
public class MomentController {

    private final MomentService momentService;

    @Autowired
    public MomentController(MomentService momentService) {
        this.momentService = momentService;
    }

    @GetMapping("/covers")
    public Result<PageDTO> fetchMomentTopics(MomentCoverQuery query) {
        PageDTO result = momentService.obtainMomentCovers(null, query);
        return ResultUtil.ok(result);
    }

    @GetMapping("/{momentId}")
    public Result<MomentDetailDTO> fetchMomentDetail(@PathVariable("momentId") Long momentId, HttpServletRequest request) {
        Long userId = null;
        if (request.getHeader("userId") != null) {
            userId = Long.valueOf(request.getHeader("userId"));
        }
        MomentDetailDTO result = momentService.obtainMomentDetail(momentId, userId);
        return ResultUtil.ok(result);
    }

    @PostMapping("/")
    public Result<Object> postNewMoment(
            @RequestParam("userId") Long userId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("spot") Long spot,
            @RequestParam("pics[]") MultipartFile[] pics
    ) {
        PostMomentForm form = new PostMomentForm();
        form.setUserId(userId);
        form.setContent(content);
        form.setTitle(title);
        form.setSpot(spot);
        form.setPics(pics);
        momentService.addNewMoment(form);
        return ResultUtil.ok();
    }

    @DeleteMapping("/{momentId}")
    public Result<Object> deleteMoment(@NotNull @PathVariable("momentId") Long momentId) {
        momentService.deleteMoment(momentId);
        return ResultUtil.ok();
    }

    @PutMapping("/unlock/{momentId}")
    public Result<Object> enableMoment(@NotNull @PathVariable("momentId") Long momentId) {
        momentService.makeMomentEnable(momentId);
        return ResultUtil.ok();
    }

    @PutMapping("/lock/{momentId}")
    public Result<Object> disableMoment(@NotNull @PathVariable("momentId") Long momentId) {
        momentService.makeMomentDisable(momentId);
        return ResultUtil.ok();
    }
}
