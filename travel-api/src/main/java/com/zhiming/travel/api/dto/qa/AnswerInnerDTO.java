package com.zhiming.travel.api.dto.qa;

import com.zhiming.travel.api.dto.picture.PictureDTO;
import lombok.Data;

import java.util.List;

@Data
public class AnswerInnerDTO {
    private Long answerId;
    private Long authorId;
    private String nickname;
    private String avatar;
    private String content;
    private List<PictureDTO> pictures;
}
