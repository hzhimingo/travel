package com.zhiming.travel.api.dto.moment;

import com.zhiming.travel.api.dto.picture.PictureDTO;
import com.zhiming.travel.api.dto.spot.SpotLocationDTO;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class MomentCoverDTO {
    private Long momentId;
    private String coverText;
    private String authorName;
    private String authorAvatar;
    private PictureDTO coverImage;
    private String location;
    private Boolean isFavorite;
    private Integer favoriteNum;
}
