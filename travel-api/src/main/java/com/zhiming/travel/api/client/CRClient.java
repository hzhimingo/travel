package com.zhiming.travel.api.client;

import com.zhiming.travel.api.dto.cr.CommentCoverDTO;
import com.zhiming.travel.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("travel-cr-service")
public interface CRClient {
    @GetMapping("/comment/cover/{id}")
    Result<List<CommentCoverDTO>> fetchCommentCover(@PathVariable("id") Long id);
    @GetMapping("/comment/count/{id}")
    Result<Integer> fetchCommentCount(@PathVariable("id") Long serviceBusinessId);
}
