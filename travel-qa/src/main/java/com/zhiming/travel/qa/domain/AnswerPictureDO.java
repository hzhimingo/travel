package com.zhiming.travel.qa.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * t_answer_picture
 * @author 
 */
@Data
public class AnswerPictureDO implements Serializable {
    private Long answerId;

    private Long pictureId;

    private static final long serialVersionUID = 1L;
}