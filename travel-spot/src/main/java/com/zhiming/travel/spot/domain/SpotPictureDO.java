package com.zhiming.travel.spot.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author HuangZhiMing
 */

@Data
@AllArgsConstructor
public class SpotPictureDO implements Serializable {
    private Long spotId;
    private Long pictureId;
    private Integer isCover;
}