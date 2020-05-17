package com.zhiming.travel.api.dto.qa;

import lombok.Data;

@Data
public class QuestionCoverDTO {
    private Long questionId;
    private String title;
    private Integer visitedNum;
    private Integer answerNum;
    private Integer followNum;
    private AnswerInnerDTO answer;
}
