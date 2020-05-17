package com.zhiming.travel.api.form.moment;

import lombok.Data;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class PostMomentForm {
    private Long userId;
    private String title;
    private String content;
    private MultipartFile[] pics;
    private Long spot;
    private Long[] topics;
}
