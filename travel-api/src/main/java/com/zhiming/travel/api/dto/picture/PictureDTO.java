package com.zhiming.travel.api.dto.picture;

import lombok.Data;

/**
 * @author HuangZhiMing
 */
@Data
public class PictureDTO {
    private Long pictureId;
    private String url;
    private Integer width;
    private Integer height;
}
