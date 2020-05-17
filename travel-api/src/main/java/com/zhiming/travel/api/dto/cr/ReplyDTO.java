package com.zhiming.travel.api.dto.cr;

import lombok.Data;

@Data
public class ReplyDTO {
    private Long replyId;
    private Long authorId;
    private String authorNickname;
    private String authorAvatar;
    private Long replyToId;
    private String replyToNickname;
    private String replyToAvatar;
    private String content;
    private Integer thumbUpNum;
    private String time;
}
