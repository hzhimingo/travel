package com.zhiming.travel.api.form.cr;

import lombok.Data;

@Data
public class PostReplyForm {
    private Long userId;
    private Long replyTo;
    private Long commentId;
    private String content;
}
