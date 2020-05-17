package com.zhiming.travel.cr.controller;

import com.zhiming.travel.api.dto.cr.CommentCoverDTO;
import com.zhiming.travel.api.dto.cr.CommentDTO;
import com.zhiming.travel.api.form.cr.PostCommentForm;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.common.vo.ResultVO;
import com.zhiming.travel.cr.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author HuangZhiMing
 */
@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comment/cover/{id}")
    public Result<List<CommentCoverDTO>> fetchCommentCover(@PathVariable("id") Long id) {
        List<CommentCoverDTO> result = commentService.obtainCommentCover(id);
        return ResultUtil.ok(result);
    }

    @GetMapping("/comment/detail")
    public Result<List<CommentDTO>> fetchCommentDetail(Long id) {
        List<CommentDTO> result = commentService.obtainComments(id);
        return ResultUtil.ok(result);
    }

    @GetMapping("/comment/count/{id}")
    public Result<Integer> fetchCommentCount(@PathVariable("id") Long id) {
        Integer result = commentService.obtainCommentCount(id);
        return ResultUtil.ok(result);
    }

    @PostMapping("/comment")
    public Result<Object> postNewComment(PostCommentForm form) {
        commentService.postNewComment(form);
        return ResultUtil.ok();
    }
}
