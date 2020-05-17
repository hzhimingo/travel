package com.zhiming.travel.api.dto.cr;

import lombok.Data;

@Data
public class CommentCoverDTO {
    private Long commentId;
    private Long authorId;
    private String authorNickname;
    private String authorAvatar;
    private String content;
    private String time;
}
