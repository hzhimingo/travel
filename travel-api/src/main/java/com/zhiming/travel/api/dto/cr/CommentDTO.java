package com.zhiming.travel.api.dto.cr;

import lombok.Data;

import java.util.List;

@Data
public class CommentDTO {
    private Long commentId;
    private Long authorId;
    private String authorNickname;
    private String authorAvatar;
    private String content;
    private String time;
    private Integer thumbUpNum;
    private Integer replyNum;
    private List<ReplyDTO> replies;
}
