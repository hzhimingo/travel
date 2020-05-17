package com.zhiming.travel.api.form.cr;

import lombok.Data;

@Data
public class PostCommentForm {
    private Long userId;
    private Long serviceBusinessId;
    private String content;
}
