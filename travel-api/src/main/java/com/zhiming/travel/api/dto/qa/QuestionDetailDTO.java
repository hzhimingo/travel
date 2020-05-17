package com.zhiming.travel.api.dto.qa;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QuestionDetailDTO {
    private Long questionId;
    private String title;
    private String content;
    private Integer visitedNum;
    private Integer answerNum;
    private String nickname;
    private LocalDate date;
    private Long authorId;
    private String avatar;
    private Boolean isCollect;
}
