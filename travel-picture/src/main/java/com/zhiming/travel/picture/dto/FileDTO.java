package com.zhiming.travel.picture.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDTO {
    private String dir;
    private String name;
    private MultipartFile file;
}
