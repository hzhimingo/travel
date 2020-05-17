package com.zhiming.travel.api.form.topic;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class PostTopicForm {
    @NotNull
    private String topicTitle;
    @NotNull
    private String introduction;
    @NotNull
    private MultipartFile pic;
}
