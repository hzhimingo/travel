package com.zhiming.travel.api.dto.user;

import lombok.Data;

/**
 * @author HuangZhiMing
 */
@Data
public class UserDTO {
    private Long userId;
    private String nickname;
    private String avatar;
    private String telephone;
    private String introduction;
    private String email;
    private String birthday;
    private String background;
    private Integer gender;
    private Integer level;
    private Integer visitedNum;
}
