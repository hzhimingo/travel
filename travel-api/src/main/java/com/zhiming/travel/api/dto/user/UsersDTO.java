package com.zhiming.travel.api.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author HuangZhiMing
 */
@Data
public class UsersDTO {
    private Long userId;
    private String nickname;
    private String telephone;
    private String email;
    private Integer gender;
    private Integer level;
    private Integer status;
    private LocalDateTime createTime;
}
