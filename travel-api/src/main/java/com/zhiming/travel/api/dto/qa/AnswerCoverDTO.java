package com.zhiming.travel.api.dto.qa;

import com.zhiming.travel.api.dto.picture.PictureDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AnswerCoverDTO {
    private Long answerId;
    private String content;
    private Integer favNum;
    private Integer starNum;
    private Integer commentNum;
    private Boolean isFav;
    private Boolean isStar;
    private LocalDate date;
    private Long authorId;
    private String nickname;
    private String avatar;
    private List<PictureDTO> pictures;
}
