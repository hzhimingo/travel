package com.zhiming.travel.api.form.picture;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadSinglePictureForm {
    private Long userId;
    private MultipartFile picture;
}
