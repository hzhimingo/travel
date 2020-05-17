package com.zhiming.travel.moment.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * t_moment_picture
 * @author 
 */
@Data
public class MomentPictureDO  implements Serializable {
    private Long momentId;

    private Long pictureId;

    private Integer isCover;

    private static final long serialVersionUID = 1L;
}