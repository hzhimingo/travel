package com.zhiming.travel.api.dto.spot;

import com.zhiming.travel.api.dto.picture.PictureDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author HuangZhiMing
 */
@Data
public class SpotDTO {
    private Long spotId;
    private String spotName;
    private Integer type;
    private String address;
    private Integer score;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String introduction;
    private String country;
    private String province;
    private String city;
    private String area;
    private List<PictureDTO> pictures;
    private String keywords;
    private Integer starNum;
}
