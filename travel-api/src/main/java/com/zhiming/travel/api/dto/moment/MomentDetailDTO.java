package com.zhiming.travel.api.dto.moment;

import com.zhiming.travel.api.dto.cr.CommentCoverDTO;
import com.zhiming.travel.api.dto.picture.PictureDTO;
import com.zhiming.travel.api.dto.spot.SpotLocationDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MomentDetailDTO {
    private Long momentId;
    private String title;
    private String content;
    private Long authorId;
    private String nickname;
    private String avatar;
    private List<PictureDTO> pictures;
    private Integer starNum;
    private Integer favNum;
    private Integer commentNum;
    private Boolean isFav;
    private Boolean isCollect;
    private List<CommentCoverDTO> comments;
    private LocalDate releaseDate;
    private SpotLocationDTO spot;
}
