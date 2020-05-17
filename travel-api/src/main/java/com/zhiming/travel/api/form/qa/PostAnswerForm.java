package com.zhiming.travel.api.form.qa;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostAnswerForm {
    private Long userId;
    private Long questionId;
    private String content;
    private MultipartFile[] pics;
}
