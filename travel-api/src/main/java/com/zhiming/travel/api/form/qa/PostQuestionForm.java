package com.zhiming.travel.api.form.qa;

import lombok.Data;

@Data
public class PostQuestionForm {
    private Long userId;
    private String title;
    private String content;
}
