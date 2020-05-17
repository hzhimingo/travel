package com.zhiming.travel.api.dto.qa;

import com.zhiming.travel.api.dto.cr.CommentCoverDTO;
import com.zhiming.travel.api.dto.picture.PictureDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AnswerDetailDTO {
    private Long answerId;
    private String content;
    private Long authorId;
    private String nickname;
    private String avatar;
    private LocalDate date;
    private List<PictureDTO> pictures;
    private Integer favNum;
    private Integer starNum;
    private Integer commentNum;
    private Boolean isFav;
    private Boolean isStar;
    private List<CommentCoverDTO> comments;
}
