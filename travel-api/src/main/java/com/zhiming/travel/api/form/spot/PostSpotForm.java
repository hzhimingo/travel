package com.zhiming.travel.api.form.spot;

import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author HuangZhiMing
 */
@Data
public class PostSpotForm {
    @NotNull
    private String spotName;
    @NonNull
    private Integer type;
    @NotNull
    private MultipartFile cover;
    private String introduction;
    @NotNull
    private String address;
    @NotNull
    private Integer country;
    @NotNull
    private Integer province;
    private Integer city;
    private Integer area;
    @NotNull
    private MultipartFile[] pics;
    private String keywords;
}
