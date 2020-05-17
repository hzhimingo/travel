package com.zhiming.travel.cr.controller;

import com.zhiming.travel.api.form.cr.PostReplyForm;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.common.vo.ResultVO;
import com.zhiming.travel.cr.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuangZhiMing
 */
@RestController
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/reply")
    public Result<Object> postReply(PostReplyForm form) {
        replyService.postNewReply(form);
        return ResultUtil.ok();
    }

    @GetMapping("/reply/{replyId}")
    public Result fetchReply(@PathVariable("replyId") Long replyId) {
        return ResultUtil.ok();
    }

    @DeleteMapping("/reply/{replyId}")
    public Result deleteReply(@PathVariable("replyId") Long replyId) {
        return ResultUtil.ok();
    }
}
